package com.jwell.classifiedProtection.commons.enums;

public enum FinishStateEnum {

    FINISHED(1, "完成"),
    NOT_FINISHED(2, "未完成");

    private int key;
    private String value;

    FinishStateEnum(int key) {
        this.key = key;
    }

    FinishStateEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return this.key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;

    }

    public static Integer getKey(Integer key) {
        FinishStateEnum[] enums = values();
        for (FinishStateEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        FinishStateEnum[] enums = values();
        for (FinishStateEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(FinishStateEnum.values()[new Integer(0)].getValue());
    }
}
