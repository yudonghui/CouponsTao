package com.ydh.couponstao.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ydh on 2022/8/16
 */
public class MaterialEntity implements Serializable {

    /**
     * category_id : 50011993
     * click_url : //s.click.taobao.com/t?
     * commission_rate : 12.0
     * coupon_amount : 330
     * coupon_click_url : //uland.taobao.com/coupon/edetail?
     * coupon_end_time : 1661961599000
     * coupon_remain_count : 39000
     * coupon_share_url : //uland.taobao.com/coupon/edetail?e=cH9R8zQWt2MNfLV8niU3R5TgU2jJNKOfNNtsjZw%2F%2FoK%2FZoRsgRhczp%2FyO19%2FNoLiBe3jSgMQ5tS7otvnh18jG8uRTiT9oEhVZV8pr6FWc0M1GT67%2B%2FVSfwBMhhpGmAENmMHpNfYdHdDR987RjT7sj6GYpTiv%2Fk5QDeVMPOqB3AXBYR42h%2B6SW%2Ftt4RI9nFLYUQLTp5mWgeNVbrKqp4Yn8g%3D%3D&&app_pvid=59590_33.43.45.213_845_1660630585827&ptl=floorId:13366;app_pvid:59590_33.43.45.213_845_1660630585827;tpp_pvid:6318785b-bbfe-4e50-af0a-16c1b33bd787&union_lens=lensId%3AMAPI%401660630585%40212b2dd5_0c3c_182a54bd20c_5775%4001
     * coupon_start_fee : 360.0
     * coupon_start_time : 1658937600000
     * coupon_total_count : 100000
     * item_description : 补水保湿 修护肌肤 提亮肤色 淡化细纹
     * item_id : 597649283190
     * level_one_category_id : 1801
     * level_one_category_name : 美容护肤/美体/精油
     * nick : missface旗舰店
     * pict_url : //gw.alicdn.com/bao/uploaded/i4/1029624918/O1CN01K1qNI61mCUXmwPGAD_!!0-item_pic.jpg
     * reserve_price : 369.9
     * seller_id : 1029624918
     * shop_title : missface旗舰店
     * small_images : {"string":["//img.alicdn.com/i3/1029624918/O1CN01xcdnO11mCUXp0wS2A_!!1029624918.jpg",
     * sub_title : 补水保湿 修护肌肤 提亮肤色 淡化细纹
     * title : [专柜同款]拍三件！Missface补水保湿亮肤面膜组合套装旗舰店正品
     * user_type : 1
     * volume : 50000
     * white_image :
     * zk_final_price : 369.9
     */

    private String category_id;//商品信息-叶子类目id
    private String click_url;//链接-宝贝推广链接
    private String commission_rate;//商品信息-佣金比率(%) 例：5.50
    private String coupon_amount;//优惠券（元） 若属于预售商品，该优惠券付尾款可用，付定金不可用
    private String coupon_click_url;//链接-宝贝+券二合一页面链接(该字段废弃，请勿再用)
    private String coupon_end_time;//优惠券信息-优惠券结束时间
    private String coupon_remain_count;//优惠券信息-优惠券剩余量
    private String coupon_share_url;//链接-宝贝+券二合一页面链接
    private String coupon_start_fee;//	优惠券信息-优惠券起用门槛，满X元可用。如：满299元减20元
    private String coupon_start_time;//优惠券信息-优惠券开始时间
    private String coupon_total_count;//优惠券信息-优惠券总量
    private String item_description;//商品信息-宝贝描述（推荐理由,不一定有）
    private String item_id;//	商品信息-宝贝id
    private String level_one_category_id;//商品信息-一级类目ID
    private String level_one_category_name;//	商品信息-一级类目名称
    private String nick;//	店铺信息-卖家昵称
    private String pict_url;//商品信息-商品主图
    private String reserve_price;//	商品信息-一口价
    private String seller_id;//	店铺信息-卖家id
    private String shop_title;//	店铺信息-店铺名称
    private SmallImagesBean small_images;//商品信息-商品小图列表
    private String sub_title;//商品子标题
    private String title;//	商品信息-商品标题
    private String user_type;//店铺信息-卖家类型，0表示淘宝，1表示天猫，3表示特价版
    private String volume;//商品信息-30天销量
    private String white_image;//商品信息-商品白底图
    private String zk_final_price;//折扣价（元） 若属于预售商品，付定金时间内，折扣价=预售价

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getClick_url() {
        return click_url;
    }

    public void setClick_url(String click_url) {
        this.click_url = click_url;
    }

    public String getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(String commission_rate) {
        this.commission_rate = commission_rate;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getCoupon_end_time() {
        return coupon_end_time;
    }

    public void setCoupon_end_time(String coupon_end_time) {
        this.coupon_end_time = coupon_end_time;
    }

    public String getCoupon_remain_count() {
        return coupon_remain_count;
    }

    public void setCoupon_remain_count(String coupon_remain_count) {
        this.coupon_remain_count = coupon_remain_count;
    }

    public String getCoupon_share_url() {
        return coupon_share_url;
    }

    public void setCoupon_share_url(String coupon_share_url) {
        this.coupon_share_url = coupon_share_url;
    }

    public String getCoupon_start_fee() {
        return coupon_start_fee;
    }

    public void setCoupon_start_fee(String coupon_start_fee) {
        this.coupon_start_fee = coupon_start_fee;
    }

    public String getCoupon_start_time() {
        return coupon_start_time;
    }

    public void setCoupon_start_time(String coupon_start_time) {
        this.coupon_start_time = coupon_start_time;
    }

    public String getCoupon_total_count() {
        return coupon_total_count;
    }

    public void setCoupon_total_count(String coupon_total_count) {
        this.coupon_total_count = coupon_total_count;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getLevel_one_category_id() {
        return level_one_category_id;
    }

    public void setLevel_one_category_id(String level_one_category_id) {
        this.level_one_category_id = level_one_category_id;
    }

    public String getLevel_one_category_name() {
        return level_one_category_name;
    }

    public void setLevel_one_category_name(String level_one_category_name) {
        this.level_one_category_name = level_one_category_name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPict_url() {
        return pict_url;
    }

    public void setPict_url(String pict_url) {
        this.pict_url = pict_url;
    }

    public String getReserve_price() {
        return reserve_price;
    }

    public void setReserve_price(String reserve_price) {
        this.reserve_price = reserve_price;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getShop_title() {
        return shop_title;
    }

    public void setShop_title(String shop_title) {
        this.shop_title = shop_title;
    }

    public SmallImagesBean getSmall_images() {
        return small_images;
    }

    public void setSmall_images(SmallImagesBean small_images) {
        this.small_images = small_images;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWhite_image() {
        return white_image;
    }

    public void setWhite_image(String white_image) {
        this.white_image = white_image;
    }

    public String getZk_final_price() {
        return zk_final_price;
    }

    public void setZk_final_price(String zk_final_price) {
        this.zk_final_price = zk_final_price;
    }

    public static class SmallImagesBean implements Serializable{
        private List<String> string;

        public List<String> getString() {
            return string;
        }

        public void setString(List<String> string) {
            this.string = string;
        }
    }
}
