package com.ydh.couponstao.entitys;

import com.ydh.couponstao.http.ErrorEntity;

/**
 * Created by ydh on 2022/8/23
 */
public class JdBaseEntity<T> {
    private int code;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
