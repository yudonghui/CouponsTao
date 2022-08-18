package com.ydh.couponstao.http;

/**
 * Created by zhengluping on 2018/6/6.
 */

public class BaseEntity<T> {
    private ErrorEntity error_response;
    private T data;

    public ErrorEntity getError_response() {
        return error_response;
    }

    public void setError_response(ErrorEntity error_response) {
        this.error_response = error_response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
