package com.jwell.classifiedProtection.commons.enums;

public enum ModuleNameEnum {

    ASSESS_ON_LINE(1, "在线测评"),
    UNKNOW(0, "未知");

    private int key;
    private String value;

    ModuleNameEnum(int key) {
        this.key = key;
    }

    ModuleNameEnum(int key, String value) {
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
        ModuleNameEnum[] enums = values();
        for (ModuleNameEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        ModuleNameEnum[] enums = values();
        for (ModuleNameEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(ModuleNameEnum.values()[new Integer(0)].getValue());
    }
}
