package com.ligf.lightimageloader.cache;

/**
 * Created by ligf on 2017/8/22.
 * 使用图片Uri的hashCode值作为图片的文件名（作为保存在本地文件系统的图片的唯一Id）
 */
public class HashCodeFileNameGenerator implements IFileNameGenerator{
    @Override
    public String getFileName(String imageUri) {
        return String.valueOf(imageUri.hashCode());
    }
}
