package com.jwell.classifiedProtection.commons.enums;

public enum TableNameEnum {

    BIZ_FILE_BANK(0, "biz_file_bank"),
    SYS_USER(1, "sys_user"),
    SYS_ROLE(2, "sys_role"),
    SYS_LOG(3, "sys_log"),
    BIZ_SYSTEM_GRADING(4, "biz_system_grading"),
    BIZ_SYSTEM(5, "biz_system"),
    BIZ_SECURITY_TOOL(6, "biz_security_tool"),
    BIZ_SECURITY_TEMPLATE(7, "biz_security_template"),
    BIZ_RECTIFICATION_TOOL(8, "biz_rectification_tool"),
    BIZ_RECTIFICATION_ADVICE_LIST(9, "biz_rectification_advice_list"),
    BIZ_RECTIFICATION_ADVICE_CATEGORY(10, "biz_rectification_advice_category"),
    BIZ_RECTIFICATION_ADVICE(11, "biz_rectification_advice"),
    BIZ_QUESTION_BANK(12, "biz_question_bank"),
    BIZ_PROTECTION_MATERIAL(13, "biz_protection_material"),
    BIZ_ORGANIZATION(14, "biz_organization"),
    BIZ_ENTERPRISE(15, "biz_enterprise"),
    BIZ_ASSET(16, "biz_asset"),
    BIZ_ASSESS_VERSION_ITEM(17, "biz_assess_version_item"),
    BIZ_ASSESS_TASK(18, "biz_assess_task"),
    BIZ_ASSESS_RESULT(19, "biz_assess_result"),
    BIZ_ASSESS_REPORT(20, "biz_assess_report"),
    BIZ_ASSESS_PROGRESS_DIC(21, "biz_assess_progress_dic"),
    BIZ_ARCHIVE_MATERIAL_LIST(22, "biz_archive_material_list"),
    BIZ_ARCHIVE_MATERIAL_AUDIT(23, "biz_archive_material_audit");

    private int key;
    private String value;

    TableNameEnum(int key) {
        this.key = key;
    }

    TableNameEnum(int key, String value) {
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
        TableNameEnum[] enums = values();
        for (TableNameEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        TableNameEnum[] enums = values();
        for (TableNameEnum enumTemp : enums) {
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
