package com.ligf.lightimageloader;

/**
 * Created by ligf on 2017/8/21.
 */
public class ImageLoader {

    private static ImageLoader mInstance = null;

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

}
