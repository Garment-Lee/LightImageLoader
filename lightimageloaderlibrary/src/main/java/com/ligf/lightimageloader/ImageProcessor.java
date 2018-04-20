package com.ligf.lightimageloader;

import android.graphics.Bitmap;

import com.ligf.lightimageloader.utils.ImageUtil;

/** 图片加工处理器
 * Created by ligf on 2018/4/19.
 */

public class ImageProcessor {

    private ImageProcessOption imageProcessOption;

    public ImageProcessor(ImageProcessOption imageProcessOption){
        this.imageProcessOption = imageProcessOption;
    }

    public Bitmap process(Bitmap bitmap){
        Bitmap resultBitmap = null;
        if (imageProcessOption.imageSize != null){
            resultBitmap = ImageUtil.resizeImageByMatrix(bitmap, imageProcessOption.imageSize.width, imageProcessOption.imageSize.height);
        }
        if (imageProcessOption.imageDisplayer != null){
            resultBitmap = imageProcessOption.imageDisplayer.display(resultBitmap);
        }
        return resultBitmap;
    }
}
