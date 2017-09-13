package com.ligf.lightimageloader;

import com.ligf.lightimageloader.service.LoadingImageTask;

import java.io.File;
import java.util.concurrent.ExecutorService;

/**
 * 图片加载任务管理器
 * Created by ligf on 2017/9/8.
 */
public class ImageLoaderManager {

    private ImageLoaderConfiguration mImageLoaderConfiguration = null;

    private ExecutorService mLoadNetWorkCacheImageExecutor = null;

    private ExecutorService mLoadCacheImageExecutor = null;

    public ImageLoaderManager(ImageLoaderConfiguration imageLoaderConfiguration){
        mImageLoaderConfiguration = imageLoaderConfiguration;
        mLoadCacheImageExecutor = imageLoaderConfiguration.mLoadCacheImageExecutor;
        mLoadNetWorkCacheImageExecutor = imageLoaderConfiguration.mLoadNetWorkCacheImageExecutor;
    }

    public void submit(LoadingImageTask loadingImageTask){
        String uri = loadingImageTask.mUri;
        File imageFile = mImageLoaderConfiguration.mDiskCache.get(uri);
        if (imageFile != null && imageFile.exists()){
            mLoadCacheImageExecutor.execute(loadingImageTask);
        } else {
            mLoadNetWorkCacheImageExecutor.execute(loadingImageTask);
        }
    }






}
