package com.jwell.classifiedProtection.commons.enums;

public enum AnswerResultEnum {

    COINCIDENCE(1, "符合"),
    PARTIAL_COINCIDENCE(2, "部分符合"),
    NOT_COINCIDENCE(3, "不符合"),
    NOT_APPLICABLE(4, "不适用");

    private int key;
    private String value;

    AnswerResultEnum(int key) {
        this.key = key;
    }

    AnswerResultEnum(int key, String value) {
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
        AnswerResultEnum[] enums = values();
        for (AnswerResultEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        AnswerResultEnum[] enums = values();
        for (AnswerResultEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(AnswerResultEnum.values()[new Integer(0)].getValue());
    }
}
