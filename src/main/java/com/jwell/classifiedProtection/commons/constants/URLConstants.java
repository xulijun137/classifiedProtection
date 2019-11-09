package com.jwell.classifiedProtection.commons.constants;

public interface URLConstants {

    public static final String LOCALHOST_PORT = "https://127.0.0.1:8089/";

    public static final String PROTOCOL_HTTPS = "https://";

    public static final String PROTOCOL_HTTP = "http://";

    public static final String SCAN_HOST_PORT = "10.128.32.21:10086";//10.8.4.106     10.128.32.21

    public static final String ES_HOST_PORT = "10.8.4.250:8888";

    /**
     * 新增资产扫描
     */
    public static final  String ADD_ASSET_SCAN = PROTOCOL_HTTP + SCAN_HOST_PORT +"/task/add";

    /**
     * 编辑资产扫描
     */
    public static final  String UPDATE_ASSET_SCAN = PROTOCOL_HTTP + SCAN_HOST_PORT +"/task/update";

    /**
     * 删除资产扫描
     */
    public static final  String DELETE_ASSET_SCAN = PROTOCOL_HTTP + SCAN_HOST_PORT +"/task/del";

    /**
     * 分页获取任务
     */
    public static final  String LIST_ASSET_SCAN = PROTOCOL_HTTP + SCAN_HOST_PORT +"/task/list";

    /**
     * 根据Keyword返回ES系统返回的数据
     */
    public static final  String ES = PROTOCOL_HTTP + ES_HOST_PORT +"/es";

}
