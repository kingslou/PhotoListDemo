# PhotoListDemo

##主要包括以下几个模块儿：
1. 图文消息流：
MainActivity 里使用 RecyclerView 展示图文 list ，使用 CardView 展示 item ，图片使用 FlowLayout 一个一个动态添加。
2. 查看大图：
ImageZoomActivity 里使用 ViewPage 展示大图，在图片下方有点击查看原图的按钮，提供了三种方式加载图片:
Fresco ，Picasso ，网络数据转成流再转化为 Bitmap 加载。
3. 查看原图：
ImageOrginActivity 查看原图，使用 PhotoView ，可以双击放大图片，可以长按保存图片，可以轻触结束查看图片。

##主要使用了以下几个开源库，感谢这些开源库的作者。
1. com.squareup.picasso:picasso
2. com.mcxiaoke.photoview:library
3. com.facebook.fresco:fresco
