package com.jwell.classifiedProtection.commons.enums;


public enum TaskTypeEnum {

    ONE(1, "一级"),
    TWO(2, "二级"),
    THREE(3, "三级"),
    FOUR(4, "四级");


    private int key;
    private String value;

    TaskTypeEnum(int key) {
        this.key = key;
    }

    TaskTypeEnum(int key, String value) {
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
        TaskTypeEnum[] enums = values();
        for (TaskTypeEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        TaskTypeEnum[] enums = values();
        for (TaskTypeEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(TaskTypeEnum.values()[new Integer(0)].getValue());
    }
}
