package com.ydh.couponstao.entitys;

/**
 * Created by ydh on 2022/8/16
 */
public class HomeEntity {
    private int imgRes;
    private String imgUrl;
    private String title;
    private int type;

    public HomeEntity(int imgRes, String title, int type) {
        this.imgRes = imgRes;
        this.title = title;
        this.type = type;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
