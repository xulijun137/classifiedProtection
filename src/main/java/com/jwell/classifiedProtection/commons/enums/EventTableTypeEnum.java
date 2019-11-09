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
public enum EventTableTypeEnum {

    sys_event_anomaly(1, "异常紧急事件"),
    sys_event_apt(2, "威胁紧急事件"),
    sys_event_guard(3, "防护紧急事件"),
    sys_event_loophole(4, "漏洞紧急事件"),
    sys_event_syslog(5, "日志紧急事件");


    private int key;
    private String value;

    EventTableTypeEnum(int key) {
        this.key = key;
    }

    EventTableTypeEnum(int key, String value) {
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
        EventTableTypeEnum[] enums = values();
        for (EventTableTypeEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getKey();
            }
        }
        return null;
    }

    public static String getValue(Integer key) {
        EventTableTypeEnum[] enums = values();
        for (EventTableTypeEnum enumTemp : enums) {
            if (enumTemp.getKey().equals(key)) {
                return enumTemp.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(EventTableTypeEnum.values()[new Integer(0)].getValue());
    }
}
