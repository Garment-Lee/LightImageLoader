package com.ligf.lightimageloader;

import android.graphics.Bitmap;
import android.os.Handler;

import com.ligf.lightimageloader.listener.OnLoadingListener;
import com.ligf.lightimageloader.listener.SimpleImageLoadingListener;
import com.ligf.lightimageloader.service.LoadingImageTask;

/**
 * Created by ligf on 2017/8/21.
 */
public class ImageLoader {

    private static ImageLoader mInstance = null;
    private ImageLoaderConfiguration mImageLoaderConfiguratiion = null;
    private ImageLoaderManager mImageLoaderManager = null;

    private OnLoadingListener mDefaultLoadingListener = new SimpleImageLoadingListener();

    private ImageLoader(){

    }

    public static ImageLoader getInstance(){
        if (mInstance == null){
            synchronized (ImageLoader.class){
                if (mInstance == null){
                    mInstance = new ImageLoader();
                }
            }
        }
        return  mInstance;
    }

    /**
     * 初始化ImageLoader配置参数
     * @param imageLoaderConfiguration
     */
    public void init(ImageLoaderConfiguration imageLoaderConfiguration){
        this.mImageLoaderConfiguratiion = imageLoaderConfiguration;
        mImageLoaderManager = new ImageLoaderManager(imageLoaderConfiguration);
    }

    /**
     *加载图片
     * @param uri 图片Uri
     * @param loadingListener 加载回调监听器
     */
    public void loadImage(String uri, OnLoadingListener loadingListener){
        if (loadingListener == null){
            loadingListener = mDefaultLoadingListener;
        }
        //开始加载图片回调
        loadingListener.onLoadingStarted(uri);
        //获取内存缓存图片
        Bitmap cacheBitmap = mImageLoaderConfiguratiion.mMemoryCache.get(uri);
        if (cacheBitmap == null){
            LoadingImageTask loadingImageTask = new LoadingImageTask(uri, mImageLoaderConfiguratiion, loadingListener, new Handler());
            mImageLoaderManager.submit(loadingImageTask);
        } else {
            loadingListener.onLoadingSucceeded(uri, cacheBitmap);
        }
    }

}
