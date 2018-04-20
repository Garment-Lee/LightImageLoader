package com.ligf.lightimageloader;

import android.graphics.Bitmap;
import android.os.Handler;

import com.ligf.lightimageloader.listener.OnLoadingListener;
import com.ligf.lightimageloader.listener.SimpleImageLoadingListener;
import com.ligf.lightimageloader.service.LoadingImageTask;

/** 图片加载器<p>
 *
 * Created by ligf on 2017/8/21.
 */
public class ImageLoader {

    private static ImageLoader mInstance = null;
    private ImageLoaderConfiguration mImageLoaderConfiguratiion = null;
    private ImageLoaderTaskManager mImageLoaderManager = null;

    private OnLoadingListener mDefaultLoadingListener = new SimpleImageLoadingListener();

    private ImageLoader() {

    }

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化ImageLoader配置参数
     *
     * @param imageLoaderConfiguration
     */
    public void init(ImageLoaderConfiguration imageLoaderConfiguration) {
        this.mImageLoaderConfiguratiion = imageLoaderConfiguration;
        mImageLoaderManager = new ImageLoaderTaskManager(imageLoaderConfiguration);
    }

    /**
     * 加载图片,返回的是原图的大小
     *
     * @param uri             图片Uri
     * @param loadingListener 加载回调监听器
     */
    public void loadImage(String uri, OnLoadingListener loadingListener) {
        if (loadingListener == null) {
            loadingListener = mDefaultLoadingListener;
        }
        //开始加载图片的回调
        loadingListener.onLoadingStarted(uri);
        //获取内存缓存中的图片
        Bitmap cacheBitmap = mImageLoaderConfiguratiion.mMemoryCache.get(uri).get();
        if (cacheBitmap == null) {
            LoadingImageTask loadingImageTask = new LoadingImageTask(uri, mImageLoaderConfiguratiion, loadingListener, new Handler(), null);
            mImageLoaderManager.submit(loadingImageTask);
        } else {
            loadingListener.onLoadingSucceeded(uri, cacheBitmap);
        }
    }

    /**
     * 加载图片，可以设置返回图片的大小
     * 注意：为了接口更灵活处理，ImageLoadingOption参数由用户每次调用时传进来，而不是统一配置到ImageLoaderConfiguration中
     *
     * @param uri
     * @param loadingListener
     * @param imageLoadingOption
     */
    public void loadImage(String uri, OnLoadingListener loadingListener, ImageProcessOption imageLoadingOption) {
        if (loadingListener == null) {
            loadingListener = mDefaultLoadingListener;
        }
        //开始加载图片的回调
        loadingListener.onLoadingStarted(uri);
        //获取内存缓存中的图片
        Bitmap cacheBitmap = mImageLoaderConfiguratiion.mMemoryCache.get(uri).get();
        if (cacheBitmap == null) {
            LoadingImageTask loadingImageTask = new LoadingImageTask(uri, mImageLoaderConfiguratiion, loadingListener, new Handler(), null);
            mImageLoaderManager.submit(loadingImageTask);
        } else {
            //如果有传入图片大小的配置，则返回对应大小的图片
            //但是缓存中还是缓存原图大小的图片
            if (imageLoadingOption != null ) {
                ImageProcessor imageProcessor = new ImageProcessor(imageLoadingOption);
                loadingListener.onLoadingSucceeded(uri, imageProcessor.process(cacheBitmap));
            } else {
                loadingListener.onLoadingSucceeded(uri, cacheBitmap);
            }
        }
    }

}
