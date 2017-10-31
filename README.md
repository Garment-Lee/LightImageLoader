# LightImageLoader
一个轻量级的图片加载器，该图片加载器具有高效、异步加载图片的特点；为了能快速加载图片，使用了内存缓存和文件缓存的二级缓存技术。
而且这个图片加载器具有很好的扩展性，使用者可以自定义内存缓存机制、文件缓存机制、图片文件名生成器、图片加载线程池等。

## 特点
* 多线程异步加载图片
* 使用内存缓存、文件缓存二级缓存机制，能快速加载图片。
* 自定义内存缓存机制、文件缓存机制、图片文件名生成器、图片加载线程池等。
* 加载指定大小的图片。
* 图片加载过程的监听回调。

## 用法
### 支持的Uri类型
"http://baidu.com/image.png"//来自网络的图片<br>
"file://mnt/sdcard/image.png" //来自SD卡的图片<br>




