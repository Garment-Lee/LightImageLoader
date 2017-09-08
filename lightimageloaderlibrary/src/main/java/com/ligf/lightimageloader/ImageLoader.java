package com.ligf.lightimageloader;

import com.ligf.lightimageloader.listener.OnLoadingListener;

/**
 * Created by ligf on 2017/8/21.
 */
public class ImageLoader {

    private static ImageLoader mInstance = null;
    private ImageLoaderConfiguration mImageLoaderConfiguratiion = null;
    private ImageLoaderManager mImageLoaderManager = null;

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

    }

}
