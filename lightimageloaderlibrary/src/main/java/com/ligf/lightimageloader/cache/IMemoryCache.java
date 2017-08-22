package com.ligf.lightimageloader.cache;

import android.graphics.Bitmap;

/**
 * Created by ligf on 2017/8/22.
 */
public interface IMemoryCache {

    public boolean put(String key, Bitmap value);

    public Bitmap get(String key);
}
