package com.ydh.couponstao.http;

/**
 * Created by ydh on 2022/8/15
 */
public class ErrorEntity {

    /**
     * code : 25
     * 21
     * 25：无效签名
     *
     * msg : Invalid signature
     * request_id : 16m63ayv8zb4c
     */

    private int code;
    private String msg;
    private String request_id;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
