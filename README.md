# LightImageLoader
    一个轻量级的图片加载器，该图片加载器使用简单，具有高效、异步加载图片的特点。这个图片加载器具有很好的扩展性，
    使用者可以自定义内存缓存机制、文件缓存机制、图片文件名生成器、图片加载线程池等；可以自定义图片的大小以及图片的形状。


## 特点
* 多线程异步加载图片
* 使用内存缓存、文件缓存二级缓存机制，能快速加载图片。
* 自定义内存缓存机制、文件缓存机制、图片文件名生成器、图片加载线程池等。
* 加载指定大小的图片以及图片的形状。
* 图片加载过程的监听回调。



## 框架设计结构

![](https://github.com/Garment-Lee/LightImageLoader/raw/master/imgs/lightimageloader_architecture_1.png)  




## 用法
#### 初始化Imageloader
```java
ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .setMemoryCache(new LruMemoryCache(5 * 1024 * 1024))
                .setDisCache(new BaseDiskCache())
                .setFileNameGenerator(new HashCodeFileNameGenerator())
                .build();
ImageLoader.getInstance().init(imageLoaderConfiguration);

```


#### 图片加载调用
``` java
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

```



### 支持的Uri类型
"http://baidu.com/image.png"  //来自网络的图片<br>
"file://mnt/sdcard/image.png"  //来自SD卡的图片<br>




