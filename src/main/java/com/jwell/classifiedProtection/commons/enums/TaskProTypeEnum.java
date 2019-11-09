package com.jwell.classifiedProtection.commons.enums;


public enum TaskProTypeEnum {

    ONE(1, "安全通用"),
    TWO(2, "云计算安全"),
    THREE(3, "移动互联网安全"),
    FOUR(4, "物联网安全"),
    FIVE(5, "工业控制系统安全");


    private int key;
    private String value;

    TaskProTypeEnum(int key) {
        this.key = key;
    }

    TaskProTypeEnum(int key, String value) {
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

    public static Integer getKey(Integer key) {
        TaskProTypeEnum[] enums = values();
        for (TaskProTypeEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        TaskProTypeEnum[] enums = values();
        for (TaskProTypeEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(TaskProTypeEnum.values()[new Integer(0)].getValue());
    }
}
