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

    public void init(ImageLoaderConfiguration imageLoaderConfiguration){
        this.mImageLoaderConfiguratiion = imageLoaderConfiguration;
        mImageLoaderManager = new ImageLoaderManager(imageLoaderConfiguration);
    }

    public void loadImage(String uri, OnLoadingListener loadingListener){
        if (loadingListener == null){
            loadingListener = mDefaultLoadingListener;
        }
        //开始加载图片回调
        loadingListener.onLoadingStarted(uri);
        Bitmap cacheBitmap = mImageLoaderConfiguratiion.mMemoryCache.get(uri);
        if (cacheBitmap == null){
            LoadingImageTask loadingImageTask = new LoadingImageTask(uri, loadingListener, new Handler());
            mImageLoaderManager.submit(loadingImageTask);
        } else {
            loadingListener.onLoadingSucceeded(uri, cacheBitmap);
        }

    }

}
