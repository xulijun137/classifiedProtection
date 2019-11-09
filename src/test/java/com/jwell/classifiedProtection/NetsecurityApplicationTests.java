package com.jwell.classifiedProtection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NetsecurityApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(NetsecurityApplicationTests.class);

    @Test
    public void test1(){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "a");
        map.put("price",500);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("name", "a");
        map2.put("price",1500);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("name", "b");
        map3.put("price",300);

        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        list.add(map);
        list.add(map2);
        list.add(map3);

        List<Map<String,Object>> result =new ArrayList<>();

        Map<String, List<Map<String, Object>>> glist = list.stream().
                collect(Collectors.groupingBy(e -> e.get("name").toString()));

        //list去重
        glist.forEach((k,alist)->{

            Map<String,Object> nmap=new HashMap<>();
            IntSummaryStatistics sumcc = alist.stream().
                    collect(Collectors.summarizingInt(e->Integer.valueOf(e.get("price").toString())));
            nmap.put("name", alist.get(0).get("name"));
            nmap.put("price", sumcc.getSum());//求和
            result.add(nmap);


        });

        logger.info("--------testMerge-------------");
        result.forEach(x->{
            System.out.println(x);
        });

    }

    @Test
    public void test2(){


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("price",500);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("price",1500);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("price",300);

        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        list.add(map);
        list.add(map2);
        list.add(map3);

//        List<Integer> integers = Arrays.asList(1,2,3,4,5,6,7,8,9,25);
//        IntSummaryStatistics summary = integers.stream().mapToInt(value -> value).summaryStatistics();
//        logger.info(summary.getAverage());
//        logger.info(summary.getCount());
//        logger.info(summary.getMax());
//        logger.info(summary.getMin());
//        logger.info(summary.getSum());


    }

}
