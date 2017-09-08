package com.ligf.lightimageloader.listener;

import android.graphics.Bitmap;

/**
 * 图片加载回调监听器
 * Created by ligf on 2017/9/8.
 */
public interface OnLoadingListener {

    void onLoadingStarted(String uri);

    void onLoadingSucceeded(String uri, Bitmap bitmap);

    void onLoadingFailed(String uri);
}
