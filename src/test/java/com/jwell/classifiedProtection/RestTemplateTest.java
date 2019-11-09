package com.jwell.classifiedProtection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClassifiedProtectionApplication.class)
public class RestTemplateTest {

    private static Logger logger = LoggerFactory.getLogger(RestTemplateTest.class);

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void restTemplateTest1() {

        try {
            RestTemplate restTemplate = new RestTemplate();
            //将指定的url返回的参数自动封装到自定义好的对应类对象中

            String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel={tel}";
            Map<String, String> map = new HashMap<>();
            map.put("tel", "15828110490");

            String notice = restTemplate.getForObject(url,
                    String.class, map);
            logger.info(notice);
        }catch (HttpClientErrorException e){
            logger.info("http客户端请求出错了！"+e.getStackTrace());
            //开发中可以使用统一异常处理，或者在业务逻辑的catch中作响应
        }
    }
}
