package com.ligf.lightimageloader.cache;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;

/**
 * Created by ligf on 2017/8/22.
 */
public interface IMemoryCache {

    public boolean put(String key, SoftReference<Bitmap> value);

    public SoftReference<Bitmap> get(String key);
}
