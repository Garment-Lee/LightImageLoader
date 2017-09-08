package com.ligf.lightimageloader.cache;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ligf on 2017/8/21.
 * 文件缓存的实现，默认使用hashCode文件名生成器
 */
public class BaseDiskCache implements IDiskCache {

    private static final int DEFAULT_BUFFER_SIZE = 32 * 1024;//32kb
    public static final int DEFAULT_COMPRESS_QUALITY = 100;

    private File mCacheDirectory = null;
    private IFileNameGenerator mFileNameGenerator = null;

    public BaseDiskCache(File cacheDir) {
        this(cacheDir, new HashCodeFileNameGenerator());
    }

    public BaseDiskCache(File cacheDir, IFileNameGenerator fileNameGenerator) {
        this.mCacheDirectory = cacheDir;
        this.mFileNameGenerator = fileNameGenerator;
    }

    @Override
    public File getDirectory() {
        return mCacheDirectory;
    }

    @Override
    public File get(String imageUri) {
        File imageFile = new File(mCacheDirectory, mFileNameGenerator.getFileName(imageUri));
//        if (imageFile.exists()) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
//            return bitmap;
//        }
        return imageFile;
    }

    @Override
    public void save(String imageUri, InputStream inputStream) {
        File imageFile = new File(mCacheDirectory, mFileNameGenerator.getFileName(imageUri));
        File tempFile = new File(mCacheDirectory, mFileNameGenerator.getFileName(imageUri) + ".temp");
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
            byte[] buff = new byte[DEFAULT_BUFFER_SIZE];
            int count;
            try{
                while ((count = inputStream.read(buff, 0, buff.length)) != -1) {
                    outputStream.write(buff, 0, count);
                }
            } finally {
                outputStream.flush();
                outputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tempFile.exists()){
                tempFile.renameTo(imageFile);
            }
        }
    }

    @Override
    public void save(String imageUri, Bitmap bitmap) throws IOException {
        File imageFile = new File(mCacheDirectory, mFileNameGenerator.getFileName(imageUri));
        File tempFile = new File(mCacheDirectory, mFileNameGenerator.getFileName(imageUri) + ".temp");
        try {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, DEFAULT_COMPRESS_QUALITY, outputStream);
            } finally {
                outputStream.flush();
                outputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (tempFile.exists()){
                tempFile.renameTo(imageFile);
            }
        }
    }
}
