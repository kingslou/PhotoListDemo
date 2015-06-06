package cn.easydone.photolistdemo;

import java.io.Serializable;

/**
 * Created by liang on 15/6/5.
 */
public class ImagesItem implements Serializable {

    private String content;
    private String images;//以分号分割的图片地址字符串

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
