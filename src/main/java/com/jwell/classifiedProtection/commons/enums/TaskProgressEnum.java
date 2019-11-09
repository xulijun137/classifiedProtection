package com.jwell.classifiedProtection.commons.enums;

public enum TaskProgressEnum {

    ORGANIZATION_INFO(1, "单位信息"),
    SYSTEM_REGISTER(2, "系统登记"),
    SYSTEM_GRADING(3, "系统定级"),
    ARCHIVE_MATERIAL(4, "备案材料"),
    RELATE_ASSET(5, "关联资产"),
    ONLIE_ASSESS(6, "在线评测"),
    ASSESS_RESULT(7, "评测结果"),
    RECTIFICATION_ADVICE(8, "整改建议");

    private int key;
    private String value;

    TaskProgressEnum(int key) {
        this.key = key;
    }

    TaskProgressEnum(int key, String value) {
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
        TaskProgressEnum[] enums = values();
        for (TaskProgressEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        TaskProgressEnum[] enums = values();
        for (TaskProgressEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(TaskProgressEnum.values()[new Integer(0)].getValue());
    }
}
