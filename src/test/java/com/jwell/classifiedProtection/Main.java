package com.jwell.classifiedProtection;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        //将List<Map>变成一个map
        //mergeListmapToOnemap(null);

        //将两个List<Map>合并成一个List<Map>，“name”为map的key
        //mergeTwoListmapToOneListmap(null, null, "name");

        //对List<Map>分组统计
        summaryGroup();
    }

    /**
     * 对List<map> 进行分组合并，按某个相同的key进行合并，并sum某个key，
     * 类似单表group by 功能
     */
    public static void summaryGroup() {
        Map<String, Object> m1 = new HashMap<>();
        Map<String, Object> m2 = new HashMap<>();
        Map<String, Object> m3 = new HashMap<>();
        Map<String, Object> m4 = new HashMap<>();
        Map<String, Object> m5 = new HashMap<>();

        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> result = new ArrayList<>();

        m1.put("type", "xm");
        m1.put("grade", 1);
        m1.put("total", 100);

        m2.put("type", "xm");
        m2.put("grade", 2);
        m2.put("total", 90);

        m3.put("type", "xm");
        m3.put("grade", 3);
        m3.put("total", 80);

        m4.put("type", "zs");
        m4.put("grade", 2);
        m4.put("total", 105);

        m5.put("type", "zs");
        m5.put("grade", 3);
        m5.put("total", 100);

        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        list.add(m5);

        list.forEach(x -> {
            System.out.println(x);
        });
        logger.info("==================================================");


        Map<String, Object> result2 = new HashMap<>();

        Iterator<Map<String, Object>> iterator = list.iterator();
        while(iterator.hasNext()){
            Map<String, Object> next = iterator.next();
            if(next.containsKey("type")) {

                Map<String, Object> tempMap = (Map) result2.get(next.get("type"));
                if (tempMap == null) {
                    Map<String, Object> hashMap = new HashMap<>();
                    result2.put(next.get("type").toString(), hashMap);
                    hashMap.put(next.get("grade").toString(), (Integer) next.get("total"));

                } else {
                    Integer total = (Integer) tempMap.get(next.get("grade"));
                    if(total!=null){
                        tempMap.put(next.get("grade").toString(), (Integer) next.get("total") + total);
                    }else{
                        tempMap.put(next.get("grade").toString(), (Integer) next.get("total"));
                    }

                }
            }
        }

        logger.info(JSONObject.toJSON(result2).toString());

        //按bb进行分组统计
//        Map<String, Map<String, List<Map<String,Object>>>> glist = list.stream().
//                collect(Collectors.groupingBy(e -> e.get("type").toString(),Collectors.groupingBy(e -> e.get("grade").toString())));

//        glist.forEach((k, slist) -> {
//            Map<String, Object> nmap = new HashMap<>();
//            IntSummaryStatistics sumcc = slist.stream().collect(Collectors.summarizingInt(e -> Integer.valueOf(e.get("total").toString())));
//            nmap.put("type", slist.get(0).get("type"));
//            nmap.put("grade", slist.get(0).get("grade"));
//            nmap.put("sum", sumcc.getSum());//求和
//            nmap.put("counts", slist.size());//计算
//            nmap.put("total", slist.get(0).get("total"));
//            result.add(nmap);
//
//
//        });

//        Map<String, List<Map<String,Object>>> countMap = list.stream().collect(Collectors.groupingBy(e -> e.get("type").toString()));
//
//        countMap.keySet().forEach(productType -> {
//            Map<String, Long> countMap1 = countMap.get(productType).stream().collect(Collectors.groupingBy(e -> e.get("grade").toString(), Collectors.counting()));
//            countMap1(key).stream().forEach(country -> {
//                Map record = new HashMap();
//                record.put("device_type", productType);
//                record.put("location", country;
//                record.put("count", countMap1(key).intValue());
//                result.add(record);
//            });
//        });

//        logger.info("--------summaryGroup-------------");
//        result.forEach(x -> {
//            logger.info(x);
//        });


    }


    /**
     * list的中map合并为一个map,即List<Map> 转为Map newMap,
     * newMap中包含了list中每个map的key与value
     */
    public static void mergeListmapToOnemap(List<Map> listmap) {

        Map<String, Object> h1 = new HashMap<>();
        h1.put("12", "fdsa");
        h1.put("123", "fdsa");
        h1.put("124", "fdsa");
        h1.put("125", "fdsa");

        Map<String, Object> h2 = new HashMap<>();
        h2.put("h12", "fdsa");
        h2.put("h123", "fdsa");
        h2.put("h124", "fdsa");
        h2.put("h125", "fdsa");

        Map<String, Object> h3 = new HashMap<>();
        h3.put("h12", "fdsa");
        h3.put("h3123", "fdsa");
        h3.put("h3124", "fdsa");
        h3.put("h3125", "fdsa");

        List<Map<String, Object>> lists = new ArrayList<>();
        lists.add(h1);
        lists.add(h2);
        lists.add(h3);

        //用java 8 把lists里面的map合并成一个新的map:
        Map<String, Object> haNew = new HashMap<>(); // 包含了h1,h2,h3的内容

        Map<String, Object> merged = lists.stream()
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .distinct()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        Stream<Object> mlist = lists.stream().map(Map::entrySet);
        Stream<Object> fmlist = lists.stream()
                .map(Map::entrySet)
                .flatMap(Set::stream);

        logger.info("merged=" + merged);

    }


    /**
     * 两个list《map》中的map合并为一个list《map》,新的list中的每个map包含了之前的两个listmap的key
     */
    public static void mergeTwoListmapToOneListmap(List<Map> list1, List<Map> list2, final String mergeKey) {

        List<Map<String, Object>> lists = new ArrayList<>();

        List<Map<String, Object>> lists1 = new ArrayList<>();
        List<Map<String, Object>> lists2 = new ArrayList<>();


        //--------------lists1--------------------
        Map<String, Object> h1 = new HashMap<>();
        h1.put("name", "fdsa0");
        h1.put("2", "fdsa0");
        h1.put("3", "fdsa0");
        h1.put("4", "fdsa0");

        Map<String, Object> h2 = new HashMap<>();
        h2.put("name", "fdsa00");
        h2.put("2", "fdsa00");
        h2.put("3", "fdsa00");
        h2.put("4", "fdsa00");

        lists1.add(h1);
        lists1.add(h2);

        //--------------lists2--------------------

        Map<String, Object> h3 = new HashMap<>();
        h3.put("name", "fdsa0");
        h3.put("21", "fdsa1");
        h3.put("31", "fdsa1");
        h3.put("41", "fdsa1");

        Map<String, Object> h4 = new HashMap<>();
        h4.put("name", "fdsa00");
        h4.put("21", "fdsa2");
        h4.put("31", "fdsa2");
        h4.put("41", "fdsa2");

        lists2.add(h3);
        lists2.add(h4);
        //测试
        //mergeKey="name";

        lists1.parallelStream().forEach(x -> {

            Map<String, Object> y2 = lists2.parallelStream().filter(y -> y.get(mergeKey).toString().equals(x.get(mergeKey).toString()))
                    .findFirst().get();

            List<Map<String, Object>> sublist = Arrays.asList(x, y2);


            Map<String, Object> merged = sublist.stream()
                    .map(Map::entrySet)
                    .flatMap(Set::stream)
                    .distinct()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            lists.add(merged);

        });
        logger.info("----------list--mergetMap---------");
        lists.forEach(x -> {
            System.out.println(x);

        });

    }
}
