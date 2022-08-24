package com.ydh.couponstao.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ydh on 2022/8/24
 */
public class ImageInfoContentEntity implements Serializable {
    private List<ImageInfoEntity> imageList;
    private String whiteImage;//白底图

    public String getWhiteImage() {
        return whiteImage;
    }

    public void setWhiteImage(String whiteImage) {
        this.whiteImage = whiteImage;
    }

    public List<ImageInfoEntity> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageInfoEntity> imageList) {
        this.imageList = imageList;
    }

    public static class ImageInfoEntity implements Serializable {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
