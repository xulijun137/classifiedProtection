package com.jwell.classifiedProtection.commons.utils;

import com.jwell.classifiedProtection.entry.FileBank;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDownloadUtils {

    /**
     * 文件批量压缩
     *
     * @param parms
     */
    public static void batchFileToZIP(List<FileBank> parms, ByteArrayOutputStream byteOutPutStream) {

        ZipOutputStream zipOut = new ZipOutputStream(byteOutPutStream);

        try {
            for (FileBank value : parms) {
                // 使用指定名称创建新的 ZIP 条目 （通俗点就是文件名）
                ZipEntry zipEntry = new ZipEntry(value.getFileName());
                // 开始写入新的 ZIP 文件条目并将流定位到条目数据的开始处
                zipOut.putNextEntry(zipEntry);
                zipOut.write(value.getFileBlob());
                zipOut.closeEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zipOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ResponseEntity下载文件
     *
     * @param fileName
     * @param byteOutPutStream
     */
    public static ResponseEntity<byte[]> downloadZIP(String fileName, ByteArrayOutputStream byteOutPutStream) {

        //下载文件
        //String fileName = "批量下载【备案材料】.zip";
        HttpHeaders headers = new HttpHeaders();
        try {
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);// 文件名称

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(byteOutPutStream.toByteArray(), headers, HttpStatus.CREATED);
        return responseEntity;
    }

    
}
