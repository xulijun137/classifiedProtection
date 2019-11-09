package com.jwell.classifiedProtection.commons.enums;

public enum ResponseCodeEnum {

    TOKEN_ERROR(911, "token错误，请重新登陆");

    private int key;
    private String value;

    ResponseCodeEnum(int key) {
        this.key = key;
    }

    ResponseCodeEnum(int key,String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return this.key;
    }

    public void setKey(Integer key){
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value){
        this.value =value;

    }

}
