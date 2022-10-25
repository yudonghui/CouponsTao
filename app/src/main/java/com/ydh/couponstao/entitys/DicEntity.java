package com.ydh.couponstao.entitys;

/**
 * Created by ydh on 2022/10/19
 */
public class DicEntity {
    private String name;
    private String value;

    public DicEntity() {
    }

    public DicEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
