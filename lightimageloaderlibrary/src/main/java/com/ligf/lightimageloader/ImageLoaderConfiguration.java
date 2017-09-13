package com.ligf.lightimageloader;

import android.app.ActivityManager;
import android.content.Context;

import com.ligf.lightimageloader.cache.BaseDiskCache;
import com.ligf.lightimageloader.cache.HashCodeFileNameGenerator;
import com.ligf.lightimageloader.cache.IDiskCache;
import com.ligf.lightimageloader.cache.IFileNameGenerator;
import com.ligf.lightimageloader.cache.IMemoryCache;
import com.ligf.lightimageloader.cache.LruMemoryCache;
import com.ligf.lightimageloader.utils.FileUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载配置器
 * Created by ligf on 2017/8/21.
 */
public class ImageLoaderConfiguration {

    public IFileNameGenerator mFileNameGenerator = null;

    public IMemoryCache mMemoryCache = null;

    public IDiskCache mDiskCache = null;

    public ExecutorService mLoadNetWorkCacheImageExecutor = null;

    public ExecutorService mLoadCacheImageExecutor = null;

    private ImageLoaderConfiguration(Builder builder){
        mFileNameGenerator = builder.fileNameGenerator ;
        mMemoryCache = builder.memoryCache;
        mDiskCache = builder.diskCache;
        mLoadCacheImageExecutor = builder.loadCacheImageExecutor;
        mLoadNetWorkCacheImageExecutor = builder.loadNetWorkCacheImageExecutor;
    }

    public static class Builder{

        private Context context = null;

        /**图片文件名生成器*/
        private IFileNameGenerator fileNameGenerator = null;

        /**内存缓存*/
        private IMemoryCache memoryCache = null;

        /**磁盘缓存*/
        private IDiskCache diskCache = null;

        /**用于加载网络图片的线程池*/
        private ExecutorService loadNetWorkCacheImageExecutor = null;

        /**用于加载文件缓存图片的线程池*/
        private ExecutorService loadCacheImageExecutor = null;

        public Builder(Context context){
            this.context = context.getApplicationContext();
        }

        public Builder setFileNameGenerator(IFileNameGenerator fileNameGenerator){
            this.fileNameGenerator = fileNameGenerator;
            return this;
        }

        public Builder setMemoryCache(IMemoryCache memoryCache){
            this.memoryCache = memoryCache;
            return this;
        }

        public Builder setDisCache(IDiskCache disCache){
            this.diskCache = disCache;
            return this;
        }

        public Builder setLoadNetWorkCacheImageExecutor(ExecutorService loadNetWorkCacheImageExecutor){
            this.loadNetWorkCacheImageExecutor = loadNetWorkCacheImageExecutor;
            return  this;
        }

        public Builder setLoadCacheImageExecutor(ExecutorService loadCacheImageExecutor){
            this.loadCacheImageExecutor = loadCacheImageExecutor;
            return this;
        }

        public ImageLoaderConfiguration build(){
            initNullFieldWithDefaultConfiguration();
            return new ImageLoaderConfiguration(this);
        }

        private void initNullFieldWithDefaultConfiguration(){
            if (this.fileNameGenerator == null){
                this.fileNameGenerator = new HashCodeFileNameGenerator();
            }

            if (this.memoryCache == null){
                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                int memoryClass = am.getMemoryClass();
                //最大内存的1/8
                int memorySize = 1024 * 1024 * memoryClass / 8;
                this.memoryCache = new LruMemoryCache(memorySize);
            }

            if (this.diskCache == null){
                this.diskCache = new BaseDiskCache(FileUtil.getExternalFilesDir(context,"CacheImage"));
            }

            if (this.loadCacheImageExecutor == null){
                loadCacheImageExecutor = Executors.newCachedThreadPool();
            }

            if (this.loadNetWorkCacheImageExecutor == null){
                loadNetWorkCacheImageExecutor = Executors.newCachedThreadPool();
            }
        }
    }
}
