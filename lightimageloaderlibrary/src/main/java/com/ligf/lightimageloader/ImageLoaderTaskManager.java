package com.ligf.lightimageloader;

import com.ligf.lightimageloader.service.LoadingImageTask;

import java.io.File;
import java.util.concurrent.ExecutorService;

/**
 * 图片加载任务管理器<p>
 * 网络图片的加载任务添加到mLoadNetWorkCacheImageExecutor线程池中，文件缓存的图片加载任务添加到mLoadCacheImageExecutor线程池中
 * Created by ligf on 2017/9/8.
 */
public class ImageLoaderTaskManager {

    private ImageLoaderConfiguration mImageLoaderConfiguration = null;

    private ExecutorService mLoadNetWorkCacheImageExecutor = null;

    private ExecutorService mLoadCacheImageExecutor = null;

    public ImageLoaderTaskManager(ImageLoaderConfiguration imageLoaderConfiguration){
        mImageLoaderConfiguration = imageLoaderConfiguration;
        mLoadCacheImageExecutor = imageLoaderConfiguration.mLoadCacheImageExecutor;
        mLoadNetWorkCacheImageExecutor = imageLoaderConfiguration.mLoadNetWorkCacheImageExecutor;
    }

    /**
     * 添加加载图片任务到线程池中
     * @param loadingImageTask
     */
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
