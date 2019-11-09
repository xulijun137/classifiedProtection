package com.jwell.classifiedProtection.commons.enums;

/**  APT中攻击协议字段app_type
     标识	名称	含义
     0	UNKNOW	未知
     2	WEB	Web应用
     7	FTP	ftp应用
     8	SMTP	Smtp应用
     9	POP3	Pop应用
     21	IMAP	Imap应用
     20	SMB	Samba应用
     30	DNS	DNS应用
 */
public enum LoopholeGradetEnum {

    HIGH("高", "high"),
    MIDDLE("中", "middle"),
    LOW("低", "low");


    private String key;
    private String value;

    LoopholeGradetEnum(String key) {
        this.key = key;
    }

    LoopholeGradetEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value){
        this.value =value;

    }

    public static String getValue(String key) {
        LoopholeGradetEnum[] enums = values();
        for (LoopholeGradetEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(LoopholeGradetEnum.values()[0].getValue());
    }
}
