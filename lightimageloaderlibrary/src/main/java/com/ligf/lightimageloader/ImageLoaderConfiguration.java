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

/**
 * Created by ligf on 2017/8/21.
 */
public class ImageLoaderConfiguration {

    private IFileNameGenerator mFileNameGenerator = null;

    private IMemoryCache mMemoryCache = null;

    private IDiskCache mDiskCache = null;

    private ImageLoaderConfiguration(Builder builder){
        mFileNameGenerator = builder.fileNameGenerator ;
        mMemoryCache = builder.memoryCache;
        mDiskCache = builder.diskCache;
    }

    public static class Builder{

        private Context context = null;

        private IFileNameGenerator fileNameGenerator = null;

        private IMemoryCache memoryCache = null;

        private IDiskCache diskCache = null;

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
                this.diskCache = new BaseDiskCache(FileUtil.getDefCacheDirectory(context));
            }
        }

    }
}
