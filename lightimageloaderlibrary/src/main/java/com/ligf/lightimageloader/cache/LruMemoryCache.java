package com.ligf.lightimageloader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/** 内存缓存<p>
 *  使用最近最少用算法进行内存大小的管理
 * Created by ligf on 2017/8/22.
 */
public class LruMemoryCache implements IMemoryCache{

    /**最大的缓存内存，默认最大为5M，单位：byte*/
    private int mMaxSize = 5 * 1024 * 1024;

    /**内存缓存集合*/
    private final LinkedHashMap<String, SoftReference<Bitmap>> mCacheMap;

    /**当前内存缓存的大小*/
    private int mCacheSize;

    public LruMemoryCache(int maxSize){
        mMaxSize = maxSize;
        mCacheMap = new LinkedHashMap<String, SoftReference<Bitmap>>();
        LruCache<String, SoftReference<Bitmap>> lruCache = new LruCache<>(mMaxSize);
    }

    @Override
    public boolean put(String key, SoftReference<Bitmap> value) {
        if (key != null && value != null){
            synchronized (this){
                //当map中已存在对应的key-value键值对，返回的previous为替换前的value；否则返回null
                SoftReference<Bitmap> previous = mCacheMap.put(key, value);
                if (previous == null){
                    mCacheSize += sizeof(previous);
                    calCacheMemorySize(mCacheSize);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SoftReference<Bitmap> get(String key) {
        if (key != null){
            synchronized (this){
                return mCacheMap.get(key);
            }
        }
        return null;
    }

    /**
     * 计算bitmap的字节数
     * @param bitmap
     * @return
     */
    private int sizeof(SoftReference<Bitmap> bitmap){
        return bitmap.get().getRowBytes() * bitmap.get().getHeight();
    }

    /**
     * 计算内存缓存是否超过了最大的缓存大小<p>
     * 如果是，则删除集合最旧的数据（HashMap中的第一个数据）
     *
     * @param size
     */
    private void calCacheMemorySize(int size){
        while (true){
            String key;
            SoftReference<Bitmap> value;
            synchronized (this){
                if (mCacheMap == null){
                    return;
                }
                if (mCacheSize < mMaxSize){
                    return;
                }
                Map.Entry<String, SoftReference<Bitmap>> entry = mCacheMap.entrySet().iterator().next();
                key = entry.getKey();
                value = entry.getValue();
                mCacheMap.remove(key);
                mCacheSize -= sizeof(value);
            }
        }
    }
}
