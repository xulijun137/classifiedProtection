package com.jwell.classifiedProtection.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * 发送短信
 *
 * @author RonnieXu
 * @since 2019/9/3
 */
@Slf4j
@Component
public class SMSUtil {

    private static final String SMS_URL = "http://10.130.0.65:8052/jplat-sms-client/sms/sendVerifyCode";
    private static final String SYSTEM_CODE = "jwell-aq";
    private static final String TEMPLATE_CODE = "jwell-aq-verifyCode";

//    private static RestTemplate staticRestTemplate;

    @Autowired
    private RestTemplate restTemplate;

//    @PostConstruct
//    public void init() {
//        staticRestTemplate = restTemplate;
//    }

    public JSONObject sendVerifyCode(String phone, String verifyCode) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone);
        jsonObject.put("systemCode", SYSTEM_CODE);
        jsonObject.put("templateCode", TEMPLATE_CODE);

        JSONObject content = new JSONObject();
        content.put("verifyCode", verifyCode);
        content.put("effectiveTime", 5);//系统设置是3分钟
        jsonObject.put("content", content);

        JSONObject params = new JSONObject();
        params.put("params", jsonObject);

        String jsonString = JSON.toJSONString(params);
        log.info("请求的对象：{}", jsonString);

        try {
//            //设置Http Header
//            HttpHeaders headers = new HttpHeaders();
//            //设置请求媒体数据类型
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            //设置返回媒体数据类型
//            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//            HttpEntity<Object> formEntity = new HttpEntity<>(params, headers);

            JSONObject json = restTemplate.postForEntity(SMS_URL, params, JSONObject.class).getBody();
            return json;
        } catch (Exception e) {
            log.error("请求接口错误：{}", e.getMessage());
        }
        return null;
    }

    public static String getVerifyCode4() {

        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 6; i++) {
            //获取数据的范围：[0,10) 包括0,不包括10
            sb.append(new Random().nextInt(10));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }


    public static void main(String[] args) {
        String code = SMSUtil.getVerifyCode4();
        log.info("验证码是：{code}", code);
    }


}
