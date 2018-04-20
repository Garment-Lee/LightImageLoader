package com.ligf.lightimageloader.displayer;

import android.graphics.Bitmap;

import com.ligf.lightimageloader.utils.ImageUtil;

/**
 *  圆形图片
 * Created by ligf on 2018/4/19.
 */

public class RoundedBitmapDisplayer implements ImageDisplayer{
    @Override
    public Bitmap display(Bitmap bitmap) {
        return ImageUtil.createCircleImage(bitmap);
    }
}
