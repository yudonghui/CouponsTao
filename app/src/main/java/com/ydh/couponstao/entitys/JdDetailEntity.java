package com.ydh.couponstao.entitys;

import java.util.List;

/**
 * Created by ydh on 2022/8/24
 */
public class JdDetailEntity {
    private String detailImages;
    private ImageInfoContentEntity imageInfo;//第一个图片链接为主图链接
    private BaseBigFieldInfoEntity baseBigFieldInfo;

    public String getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(String detailImages) {
        this.detailImages = detailImages;
    }

    public ImageInfoContentEntity getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfoContentEntity imageInfo) {
        this.imageInfo = imageInfo;
    }

    public BaseBigFieldInfoEntity getBaseBigFieldInfo() {
        return baseBigFieldInfo;
    }

    public void setBaseBigFieldInfo(BaseBigFieldInfoEntity baseBigFieldInfo) {
        this.baseBigFieldInfo = baseBigFieldInfo;
    }

    public static class BaseBigFieldInfoEntity {
        private String wareQD;
        private String wdis;

        public String getWdis() {
            return wdis;
        }

        public void setWdis(String wdis) {
            this.wdis = wdis;
        }

        public String getWareQD() {
            return wareQD;
        }

        public void setWareQD(String wareQD) {
            this.wareQD = wareQD;
        }
    }
}
