package com.ligf.lightimageloader.cache;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ligf on 2017/8/21.
 */
public interface IDiskCache {

    public File getDirectory();

    public File get(String imageUri);

    public void save(String imageUri, InputStream inputStream) throws FileNotFoundException;

    public void save(String imageUri, Bitmap bitmap) throws IOException;
}
