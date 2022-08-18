package com.ydh.couponstao.entitys;

import com.ydh.couponstao.http.ErrorEntity;

/**
 * Created by ydh on 2022/8/17
 */
public class TbCodeEntity {
    private ErrorEntity error_response;
    private DataBean data;

    public ErrorEntity getError_response() {
        return error_response;
    }

    public void setError_response(ErrorEntity error_response) {
        this.error_response = error_response;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String model;
        private String password_simple;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPassword_simple() {
            return password_simple;
        }

        public void setPassword_simple(String password_simple) {
            this.password_simple = password_simple;
        }
    }
}
