package com.ligf.lightimageloader.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.ligf.lightimageloader.ImageLoaderConfiguration;
import com.ligf.lightimageloader.ImageProcessOption;
import com.ligf.lightimageloader.ImageProcessor;
import com.ligf.lightimageloader.listener.OnLoadingListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片加载任务类<p>
 * 使用HttpUrlConnection进行网络请求<p>
 * Created by ligf on 2017/9/12.
 */
public class LoadingImageTask implements Runnable {

    public String mUri = null;
    public OnLoadingListener mOnLoadingListener = null;
    /** 用于调用回调监听器的Handler对象 */
    public Handler mHandler = null;
    public ImageLoaderConfiguration mImageLoaderConfiguration = null;
    /**图片加载属性*/
    public ImageProcessOption mImageLoadingOption = null;

    public static final int HTTP_TYPE = 1;
    public static final int FILE_TYPE = 2;
//    public static final String

    public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 5 * 1000; // milliseconds
    public static final int DEFAULT_HTTP_READING_TIMEOUT = 5 * 1000;

    /**网络请求重连次数*/
    public static final int DEFAULT_MAX_CONNECT_COUNT = 5;

    public LoadingImageTask(String uri, ImageLoaderConfiguration imageLoaderConfiguration, OnLoadingListener onLoadingListener, Handler handler, ImageProcessOption imageLoadingOption) {
        this.mUri = uri;
        mOnLoadingListener = onLoadingListener;
        mHandler = handler;
        mImageLoaderConfiguration = imageLoaderConfiguration;
        mImageLoadingOption = imageLoadingOption;
    }

    @Override
    public void run() {
        //获取文件缓存图片
        File imageFile = mImageLoaderConfiguration.mDiskCache.get(mUri);
        Bitmap bitmap = null;
        if (imageFile != null && imageFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            if (bitmap != null){
                SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                mImageLoaderConfiguration.mMemoryCache.put(mUri, softReference);
            }
        } else {
            try {
                InputStream inputStream = getStream(mUri);
                if (inputStream != null){
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
                if (bitmap != null){
                    SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                    mImageLoaderConfiguration.mMemoryCache.put(mUri, softReference);
                    mImageLoaderConfiguration.mDiskCache.save(mUri, bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bitmap != null){
            final Bitmap finalBitmap = bitmap;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //如果有传入图片处理配置，则进行图片处理
                    //但是缓存中还是缓存原图大小的图片
                    if (mImageLoadingOption != null){
                        ImageProcessor imageProcessor = new ImageProcessor(mImageLoadingOption);
                        mOnLoadingListener.onLoadingSucceeded(mUri, imageProcessor.process(finalBitmap));
                    } else {
                        mOnLoadingListener.onLoadingSucceeded(mUri, finalBitmap);
                    }
                }
            });
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mOnLoadingListener.onLoadingFailed(mUri);
                }
            });
        }
    }

    /**
     * 根据Uri获取图片文件流
     * @param uri
     * @return
     * @throws IOException
     */
    private InputStream getStream(String uri) throws IOException {
        int uriType = getUriType(uri);
        InputStream inputStream = null;
        switch (uriType) {
            case HTTP_TYPE:
                getInputStreamFromNetWork(uri);
                break;
            case FILE_TYPE:
                getInputStreamFromFile(uri);
                break;
            default:

                break;
        }
        return inputStream;
    }

    /**
     * 获取不同的uri类型包括网络类型、文件类型等
     * @param uri
     * @return
     */
    private int getUriType(String uri) {
        if (uri.startsWith("http") || uri.startsWith("https")) {
            return HTTP_TYPE;
        } else if (uri.startsWith("file://")){
            return FILE_TYPE;
        }
        return -1;
    }

    /**
     * 获取对应uri的输入流
     * @param uri
     * @return
     * @throws IOException
     */
    private InputStream getInputStreamFromNetWork(String uri) throws IOException {
        HttpURLConnection connection = getConnection(uri);
        int connectCount = 0;
        //请求失败，进行重连
        while (connection.getResponseCode() != 200 && connectCount < DEFAULT_MAX_CONNECT_COUNT){
            connection = getConnection(uri);
            connectCount ++;
        }
        InputStream inputStream = connection.getInputStream();
        return inputStream;
    }

    /**
     * 获取对应Uri的文件输入流
     * @param uri
     * @return
     */
    private InputStream getInputStreamFromFile(String uri){
        String imageUri = uri.substring("file://".length());
        File imageFile = new File(uri);
        InputStream inputStream = null;
        if (imageFile.exists()){
            try {
                inputStream = new FileInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }

    private HttpURLConnection getConnection(String uri) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri).openConnection();
        httpURLConnection.setConnectTimeout(DEFAULT_HTTP_CONNECT_TIMEOUT);
        httpURLConnection.setReadTimeout(DEFAULT_HTTP_READING_TIMEOUT);
        return httpURLConnection;
    }
}
