package com.ligf.lightimageloader.sample;

import android.app.Application;

import com.ligf.lightimageloader.ImageLoader;
import com.ligf.lightimageloader.ImageLoaderConfiguration;
import com.ligf.lightimageloader.cache.BaseDiskCache;
import com.ligf.lightimageloader.cache.HashCodeFileNameGenerator;
import com.ligf.lightimageloader.cache.LruMemoryCache;

/**
 * Created by ligf on 2018/4/20.
 */

public class ImageLoaderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .setMemoryCache(new LruMemoryCache(5 * 1024 * 1024))
                .setDisCache(new BaseDiskCache())
                .setFileNameGenerator(new HashCodeFileNameGenerator())
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }
}
