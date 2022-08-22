package com.ydh.couponstao.entitys;

import com.ydh.couponstao.http.ErrorEntity;

import java.util.List;

/**
 * Created by ydh on 2022/8/16
 */
public class MaterialContentEntity {

    /**
     *
     */
    private ErrorEntity error_response;
    private String is_default;
    private List<MaterialEntity> result_list;
    private String request_id;

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public List<MaterialEntity> getResult_list() {
        return result_list;
    }

    public void setResult_list(List<MaterialEntity> result_list) {
        this.result_list = result_list;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }



    public ErrorEntity getError_response() {
        return error_response;
    }

    public void setError_response(ErrorEntity error_response) {
        this.error_response = error_response;
    }

}
