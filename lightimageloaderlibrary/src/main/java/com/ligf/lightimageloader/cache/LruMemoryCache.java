package com.ligf.lightimageloader.cache;

import android.graphics.Bitmap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ligf on 2017/8/22.
 */
public class LruMemoryCache implements IMemoryCache{

    /**最大的缓存内存，单位：byte*/
    private final int mMaxSize;

    /**内存缓存集合*/
    private final LinkedHashMap<String, Bitmap> mCacheMap;

    /**当前内存缓存的大小*/
    private int mCacheSize;

    public LruMemoryCache(int maxSize){
        mMaxSize = maxSize;
        mCacheMap = new LinkedHashMap<String, Bitmap>();
    }

    @Override
    public boolean put(String key, Bitmap value) {
        if (key != null && value != null){
            synchronized (this){
                Bitmap previous = mCacheMap.put(key, value);
                if (previous == null){
                    mCacheSize += sizeof(previous);
                }
            }
        }
        calCacheMemorySize(mCacheSize);
        return false;
    }

    @Override
    public Bitmap get(String key) {
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
    private int sizeof(Bitmap bitmap){
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 计算内存缓存是否超过了最大的缓存大小<p>
     * 如果是，则删除集合最后一项数据
     *
     * @param size
     */
    private void calCacheMemorySize(int size){
        while (true){
            String key;
            Bitmap value;
            synchronized (this){
                if (mCacheMap == null){
                    return;
                }
                if (mCacheSize < mMaxSize){
                    return;
                }
                Map.Entry<String, Bitmap> entry = mCacheMap.entrySet().iterator().next();
                key = entry.getKey();
                value = entry.getValue();
                mCacheMap.remove(key);
                mCacheSize -= sizeof(value);
            }
        }
    }
}
