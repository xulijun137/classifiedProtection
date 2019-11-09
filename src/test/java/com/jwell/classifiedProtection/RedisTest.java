package com.jwell.classifiedProtection;

import com.jwell.classifiedProtection.components.redis.RedisUtil;
import com.jwell.classifiedProtection.entry.User;
import com.jwell.classifiedProtection.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClassifiedProtectionApplication.class)
public class RedisTest {

    private static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    IUserService iUserService;

    @Resource
    RedisUtil redisUtil;

    @Test
    public void test() {

        List<User> userList = iUserService.list(null);

        redisUtil.del("userList");
        //redisUtil.set("睡觉了喂","男神",1000*15);
        redisUtil.lSet("userList", userList, 3600);//时间周期是1小时

        //Object data = redisUtil.get("睡觉了喂");
        List<Object> userList1 = redisUtil.lGet("userList", 0, redisUtil.lGetListSize("userList"));

        //print 男神
        //logger.info("Hello------------->>>>>"+data.toString());
        logger.info("Hello------------->>>>>" + userList1.toString());
    }

    @Test
    //自减和自减
    public void fun2() {

        redisUtil.set("num", 10,1);
        Long time = redisUtil.getExpire("num");
        System.out.println(time);
//        logger.info(redisUtil.get("num"));
//
//        redisUtil.decr("num", 1);
//        logger.info(redisUtil.get("num"));
//
//        redisUtil.incr("num", 1);
//        redisUtil.incr("num", 1);
//        logger.info(redisUtil.get("num"));

    }


}
