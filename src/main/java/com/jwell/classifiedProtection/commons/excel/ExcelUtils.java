package com.jwell.classifiedProtection.commons.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.jwell.classifiedProtection.entry.excel.UserExcelModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtils {


    /**
     * @param is   导入文件输入流
     * @param clazz Excel实体映射类
     * @return
     */
    public static <T extends BaseRowModel> List<T> readExcel(InputStream is, final Class<? extends BaseRowModel> clazz){

        List<T> rowsList = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(is);
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();
            ExcelReader excelReader = EasyExcelFactory.getReader(bis, listener);
            excelReader.read(new Sheet(1, 1, clazz));
            rowsList = listener.getRows();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rowsList;
    }

    /**
     *
     * @param os 文件输出流
     * @param clazz Excel实体映射类
     * @param data 导出数据
     * @return
     */
    public static Boolean writeExcel(OutputStream os, Class clazz, List<? extends BaseRowModel> data){

        BufferedOutputStream bos= null;
        try {
            bos = new BufferedOutputStream(os);
            ExcelWriter writer = new ExcelWriter(bos, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0,clazz);
            writer.write(data, sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * ResponseEntity下载文件
     *
     * @param fileName
     * @param byteOutPutStream
     */
    public static ResponseEntity<byte[]> downloadExcel(String fileName, ByteArrayOutputStream byteOutPutStream) {

        //下载文件
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment",
                    new String(fileName.getBytes("GBK"), "ISO8859-1"));// 文件名称

            ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(byteOutPutStream.toByteArray(), headers, HttpStatus.CREATED);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 这是你最熟悉的老朋友Main方法，哈哈哈
     * @param args
     */
    public static void main(String[] args) {
        //1.导入Excel
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("c:\\test\\test1.xls");
            List<UserExcelModel> userExcelModelList = ExcelUtils.readExcel(fis, UserExcelModel.class);
            System.out.println("导入是否成功：-------------->"+"数据行数是："+userExcelModelList.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //2.导出Excel
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("D:\\export.xlsx");
            //FileOutputStream fos, Class clazz, List<? extends BaseRowModel> data
            List<UserExcelModel> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                UserExcelModel excelEntity = new UserExcelModel();
                excelEntity.setUserName("我是名字" + i);
                list.add(excelEntity);
            }
            Boolean flag = ExcelUtils.writeExcel(fos, UserExcelModel.class, list);
            System.out.println("导出是否成功：" + flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
