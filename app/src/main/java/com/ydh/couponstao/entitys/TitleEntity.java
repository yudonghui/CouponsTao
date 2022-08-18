package com.ydh.couponstao.entitys;

/**
 * Created by ydh on 2022/8/16
 */
public class TitleEntity {
    private String material_id;
    private String material_name;

    public TitleEntity(String material_id, String material_name) {
        this.material_id = material_id;
        this.material_name = material_name;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }
}
