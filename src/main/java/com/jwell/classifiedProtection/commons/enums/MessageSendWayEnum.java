package com.jwell.classifiedProtection.commons.enums;

/**
 * 消息通知方式：0系统内通知，1邮件通知，2短信通知
 */
public enum MessageSendWayEnum {

    SYSTEM(0, "系统内通知"),
    EMAIL(1, "邮件通知"),
    MESSAGE(2, "短信通知");

    private int key;
    private String value;

    MessageSendWayEnum(int key) {
        this.key = key;
    }

    MessageSendWayEnum(int key, String value) {
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
        MessageSendWayEnum[] enums = values();
        for (MessageSendWayEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        MessageSendWayEnum[] enums = values();
        for (MessageSendWayEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(MessageSendWayEnum.values()[new Integer(0)].getValue());
    }
}
