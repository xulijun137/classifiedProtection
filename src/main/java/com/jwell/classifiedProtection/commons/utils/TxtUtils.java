package com.jwell.classifiedProtection.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TxtUtils {

    private static Logger logger = LoggerFactory.getLogger(TxtUtils.class);

    public static String readTxt(String filePath) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);


                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    //logger.info(lineTxt);
                    stringBuilder.append(lineTxt);
                }
                br.close();
            } else {
                logger.info("文件不存在!");
            }
        } catch (Exception e) {
            logger.info("文件读取错误!");
        }

        return stringBuilder.toString();
    }

    public static void printStringToTxt(String str) {

        PrintWriter fw;
        try {
            fw = new PrintWriter("D://ip.txt");
            BufferedWriter bw = new BufferedWriter(fw);
//            for(int i=0;i<500000;i++){
            fw.write(str);
//            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //两个参数，一个是参数定义申明List<E>，一个是返回类型申明<E>
    public static <E> List<E> getDuplicateElements(List<E> list) {
        return list.stream() // list 对应的 Stream
                .collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet().stream() // 所有 entry 对应的 Stream
                .filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
                .map(entry -> entry.getKey()) // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList());  // 转化为 List
    }


    public static Map<String,Object> transferString2Map(String str){

        Map<String,Object> dataMap = new HashMap<>();
        if(str!=null && !"".equals(str)){
            String[] beansArrays = str.split(";");
            for(String temp:beansArrays){
                String[] beans = temp.split(":");
                if(beans.length==2){
                    dataMap.put(beans[0],beans[1]);
                }else{
                    //这行代码后期可能没用，数据端已经做了数据结构调整和优化
                    dataMap.put("desc",temp);
                }

            }
        }
        return dataMap;
    }

}
