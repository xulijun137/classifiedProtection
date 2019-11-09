package com.jwell.classifiedProtection.commons.enums;

public enum AuditStateEnum {

    AUDIT_PASSED(1, "审核通过"),
    AUDIT_NOT_PASSED(2, "审核不通过");

    private int key;
    private String value;

    AuditStateEnum(int key) {
        this.key = key;
    }

    AuditStateEnum(int key, String value) {
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
        AuditStateEnum[] enums = values();
        for (AuditStateEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        AuditStateEnum[] enums = values();
        for (AuditStateEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(AuditStateEnum.values()[new Integer(0)].getValue());
    }
}
