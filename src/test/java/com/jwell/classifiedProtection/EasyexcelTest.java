package com.jwell.classifiedProtection;

import com.jwell.classifiedProtection.commons.excel.ExcelUtils;
import com.jwell.classifiedProtection.entry.User;
import com.jwell.classifiedProtection.entry.excel.UserExcelModel;
import com.jwell.classifiedProtection.service.IUserService;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClassifiedProtectionApplication.class)
public class EasyexcelTest {

    private static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    IUserService iUserService;

    @Test
    public void readExcelModel() {

        //这一行可以知道下面获取的文件的默认文件夹路径，无其他作用
        System.out.println(System.getProperty("user.dir"));
        try{

            FileInputStream inputStream = new FileInputStream("c:\\test\\test1.xls");
            List<UserExcelModel> list = ExcelUtils.readExcel(new BufferedInputStream(inputStream), UserExcelModel.class);

            List<User> userList = new ArrayList<>();
            list.forEach(demo->{
                User user = new User(
                        demo.getUserName(),
                        demo.getPassword(),
                        demo.getRealName(),
                        demo.getPhone(),
                        demo.getEmail(),
                        demo.getRoleType(),
                        demo.getDepartmentId(),
                        demo.getRemark());
                userList.add(user);
            });
            System.out.println(list.size());
            iUserService.saveOrUpdateBatch(userList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readExcelFile() {

        //1.导入Excel
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("c:\\test\\test1.xls");
            List<UserExcelModel> userExcelModelList = ExcelUtils.readExcel(fis, UserExcelModel.class);
            System.out.println("导入是否成功：-------------->" + "数据行数是：" + userExcelModelList.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    public void exportExcelFile() {

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

    @Test
    public void exportExcelByte() {

        //2.导出Excel
        ByteOutputStream bos = new ByteOutputStream();
        try {
            List<UserExcelModel> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                UserExcelModel excelEntity = new UserExcelModel();
                excelEntity.setUserName("我是名字" + i);
                list.add(excelEntity);
            }
            Boolean flag = ExcelUtils.writeExcel(bos, UserExcelModel.class, list);
            System.out.println("导出是否成功：" + flag);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
