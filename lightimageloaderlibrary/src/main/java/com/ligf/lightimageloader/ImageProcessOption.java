package com.ligf.lightimageloader;

import com.ligf.lightimageloader.displayer.ImageDisplayer;

/**
 * Created by ligf on 2017/10/30.
 * 图片处理属性
 */
public class ImageProcessOption {

    /***指定图片大小*/
    public ImageSize imageSize;

    /** 图片的最终展示效果*/
    public ImageDisplayer imageDisplayer;

    public ImageProcessOption(ImageSize imageSize, ImageDisplayer imageDisplayer){
        this.imageSize = imageSize;
        this.imageDisplayer = imageDisplayer;
    }

}
