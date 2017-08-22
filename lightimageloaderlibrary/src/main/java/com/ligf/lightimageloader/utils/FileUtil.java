package com.ligf.lightimageloader.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Created by ligf on 2017/8/21.
 */
public class FileUtil {

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * 获取文件缓存的路径，传入文件名称
     * @param context
     * @param appCacheDirName
     * @return
     */
    public static File getCacheDirectory(Context context, String appCacheDirName){
        File cacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)){
            cacheDir = new File(Environment.getExternalStorageDirectory(), appCacheDirName);
        }
        //应用默认的缓存路径
        if (cacheDir == null || !cacheDir.exists() && !cacheDir.mkdirs()){
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }

    /**
     * 获取默认的文件缓存文件路径
     * @param context
     * @return
     */
    public static  File getDefCacheDirectory(Context context){
        File cacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)){
            cacheDir = getDefaultCacheDirectory(context);
        }
        //应用默认的缓存路径
        if (cacheDir == null || !cacheDir.exists() && !cacheDir.mkdirs()){
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }

    private static File getDefaultCacheDirectory(Context context){
        File dataCacheDir = new File(new File(Environment.getExternalStorageDirectory(),"Android"), "data");
        File appCacheDir = new File(dataCacheDir, "cache");
        if (!appCacheDir.exists()){
            appCacheDir.mkdirs();
        }
        return  appCacheDir;
    }

    /**
     * 判断是否有写入存储的权限
     * @param context
     * @return
     */
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
