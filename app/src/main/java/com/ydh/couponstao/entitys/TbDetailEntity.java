package com.ydh.couponstao.entitys;

import com.ydh.couponstao.http.ErrorEntity;

import java.util.List;

/**
 * Created by ydh on 2022/8/17
 */
public class TbDetailEntity {
    private ErrorEntity error_response;
    private List<DataBean> results;

    public ErrorEntity getError_response() {
        return error_response;
    }

    public void setError_response(ErrorEntity error_response) {
        this.error_response = error_response;
    }

    public List<DataBean> getResults() {
        return results;
    }

    public void setResults(List<DataBean> results) {
        this.results = results;
    }

    public static class DataBean {

        /**
         * cat_leaf_name : 面部护理套装
         * cat_name : 美容护肤/美体/精油
         * free_shipment : true
         * hot_flag : 1
         * item_url : https://detail.tmall.com/item.htm?id=597649283190
         * ju_online_end_time : 0
         * ju_online_start_time : 0
         * ju_pre_show_end_time : 0
         * ju_pre_show_start_time : 0
         * material_lib_type : 1,2
         * nick : missface旗舰店
         * num_iid : 597649283190
         * pict_url : https://img.alicdn.com/bao/uploaded/i4/1029624918/O1CN01K1qNI61mCUXmwPGAD_!!0-item_pic.jpg
         * presale_deposit : 0
         * presale_end_time : 0
         * presale_start_time : 0
         * presale_tail_end_time : 0
         * presale_tail_start_time : 0
         * provcity : 广东 东莞
         * reserve_price : 369.9
         * seller_id : 1029624918
         * small_images : ["https://img.alicdn.com/i3/1029624918/O1CN01xcdnO11mCUXp0wS2A_!!1029624918.jpg","https://img.alicdn.com/i4/1029624918/O1CN01Pe3MyZ1mCUa3UXAih_!!1029624918.jpg","https://img.alicdn.com/i2/1029624918/O1CN01icfK6h1mCUOILlpmd_!!1029624918.jpg","https://img.alicdn.com/i4/1029624918/O1CN01yQnXev1mCUOLaMIv9_!!1029624918.jpg"]
         * superior_brand : 0
         * title : [专柜同款]拍三件！Missface补水保湿亮肤面膜组合套装旗舰店正品
         * tmall_play_activity_end_time : 0
         * tmall_play_activity_start_time : 0
         * user_type : 1
         * volume : 50000
         * zk_final_price : 369.9
         */

        private String cat_leaf_name;
        private String cat_name;
        private String content;
        private boolean free_shipment;
        private String hot_flag;
        private String item_url;//商品链接
        private String ju_online_end_time;
        private String ju_online_start_time;
        private String ju_pre_show_end_time;
        private String ju_pre_show_start_time;
        private String material_lib_type;
        private String nick;
        private String num_iid;
        private String pict_url;
        private String presale_deposit;
        private String presale_end_time;
        private String presale_start_time;
        private String presale_tail_end_time;
        private String presale_tail_start_time;
        private String provcity;
        private String reserve_price;
        private String seller_id;
        private String superior_brand;
        private String title;
        private String tmall_play_activity_end_time;
        private String tmall_play_activity_start_time;
        private String user_type;
        private String volume;
        private String zk_final_price;
        private List<String> small_images;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCat_leaf_name() {
            return cat_leaf_name;
        }

        public void setCat_leaf_name(String cat_leaf_name) {
            this.cat_leaf_name = cat_leaf_name;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public boolean isFree_shipment() {
            return free_shipment;
        }

        public void setFree_shipment(boolean free_shipment) {
            this.free_shipment = free_shipment;
        }

        public String getHot_flag() {
            return hot_flag;
        }

        public void setHot_flag(String hot_flag) {
            this.hot_flag = hot_flag;
        }

        public String getItem_url() {
            return item_url;
        }

        public void setItem_url(String item_url) {
            this.item_url = item_url;
        }

        public String getJu_online_end_time() {
            return ju_online_end_time;
        }

        public void setJu_online_end_time(String ju_online_end_time) {
            this.ju_online_end_time = ju_online_end_time;
        }

        public String getJu_online_start_time() {
            return ju_online_start_time;
        }

        public void setJu_online_start_time(String ju_online_start_time) {
            this.ju_online_start_time = ju_online_start_time;
        }

        public String getJu_pre_show_end_time() {
            return ju_pre_show_end_time;
        }

        public void setJu_pre_show_end_time(String ju_pre_show_end_time) {
            this.ju_pre_show_end_time = ju_pre_show_end_time;
        }

        public String getJu_pre_show_start_time() {
            return ju_pre_show_start_time;
        }

        public void setJu_pre_show_start_time(String ju_pre_show_start_time) {
            this.ju_pre_show_start_time = ju_pre_show_start_time;
        }

        public String getMaterial_lib_type() {
            return material_lib_type;
        }

        public void setMaterial_lib_type(String material_lib_type) {
            this.material_lib_type = material_lib_type;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getNum_iid() {
            return num_iid;
        }

        public void setNum_iid(String num_iid) {
            this.num_iid = num_iid;
        }

        public String getPict_url() {
            return pict_url;
        }

        public void setPict_url(String pict_url) {
            this.pict_url = pict_url;
        }

        public String getPresale_deposit() {
            return presale_deposit;
        }

        public void setPresale_deposit(String presale_deposit) {
            this.presale_deposit = presale_deposit;
        }

        public String getPresale_end_time() {
            return presale_end_time;
        }

        public void setPresale_end_time(String presale_end_time) {
            this.presale_end_time = presale_end_time;
        }

        public String getPresale_start_time() {
            return presale_start_time;
        }

        public void setPresale_start_time(String presale_start_time) {
            this.presale_start_time = presale_start_time;
        }

        public String getPresale_tail_end_time() {
            return presale_tail_end_time;
        }

        public void setPresale_tail_end_time(String presale_tail_end_time) {
            this.presale_tail_end_time = presale_tail_end_time;
        }

        public String getPresale_tail_start_time() {
            return presale_tail_start_time;
        }

        public void setPresale_tail_start_time(String presale_tail_start_time) {
            this.presale_tail_start_time = presale_tail_start_time;
        }

        public String getProvcity() {
            return provcity;
        }

        public void setProvcity(String provcity) {
            this.provcity = provcity;
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

        public String getSuperior_brand() {
            return superior_brand;
        }

        public void setSuperior_brand(String superior_brand) {
            this.superior_brand = superior_brand;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTmall_play_activity_end_time() {
            return tmall_play_activity_end_time;
        }

        public void setTmall_play_activity_end_time(String tmall_play_activity_end_time) {
            this.tmall_play_activity_end_time = tmall_play_activity_end_time;
        }

        public String getTmall_play_activity_start_time() {
            return tmall_play_activity_start_time;
        }

        public void setTmall_play_activity_start_time(String tmall_play_activity_start_time) {
            this.tmall_play_activity_start_time = tmall_play_activity_start_time;
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

        public String getZk_final_price() {
            return zk_final_price;
        }

        public void setZk_final_price(String zk_final_price) {
            this.zk_final_price = zk_final_price;
        }

        public List<String> getSmall_images() {
            return small_images;
        }

        public void setSmall_images(List<String> small_images) {
            this.small_images = small_images;
        }
    }
}
