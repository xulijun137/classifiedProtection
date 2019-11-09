package com.jwell.classifiedProtection.commons.enums;

public enum MaterialFromEnum {

    USER_UPLOADED(1, "用户上传"),
    ADMIN_ADDED(2, "管理员添加"),
    SYSTEM_DEFAULT(3, "系统默认");

    private int key;
    private String value;

    MaterialFromEnum(int key) {
        this.key = key;
    }

    MaterialFromEnum(int key, String value) {
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
        MaterialFromEnum[] enums = values();
        for (MaterialFromEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        MaterialFromEnum[] enums = values();
        for (MaterialFromEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(TableNameEnum.values()[new Integer(0)].getValue());
    }
}
