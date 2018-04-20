package com.ligf.lightimageloader.sample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ligf.lightimageloader.ImageLoader;
import com.ligf.lightimageloader.ImageProcessOption;
import com.ligf.lightimageloader.ImageSize;
import com.ligf.lightimageloader.displayer.ImageDisplayer;
import com.ligf.lightimageloader.displayer.RoundedBitmapDisplayer;
import com.ligf.lightimageloader.listener.OnLoadingListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageSize imageSize = new ImageSize(400, 400);
        ImageDisplayer imageDisplayer = new RoundedBitmapDisplayer();
        ImageLoader.getInstance().loadImage("", new OnLoadingListener() {
            @Override
            public void onLoadingStarted(String uri) {

            }

            @Override
            public void onLoadingSucceeded(String uri, Bitmap bitmap) {
                //TODO:展示成功加载的图片

            }

            @Override
            public void onLoadingFailed(String uri) {

            }
        }, new ImageProcessOption(imageSize, imageDisplayer));
    }
}
