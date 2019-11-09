package com.jwell.classifiedProtection;

import com.jwell.classifiedProtection.commons.utils.FileUtils;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.jwell.classifiedProtection.service.IRectificationToolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClassifiedProtectionApplication.class)
public class BlobTest {

    @Autowired
    IRectificationToolService iRectificationToolService;

    @Autowired
    IFileBankService iFileBankService;

    @Test
    public void saveOrUpdate() {

        try {

            String fileName = "D:\\pdfTable1566556121105.pdf";
            byte[] fileToBytes = FileUtils.fileToBytes(fileName);

            Integer[] ids = {12, 15, 16};
            for (Integer id : ids) {
                FileBank fileBank = new FileBank();
                File file = new File(fileName);
                fileBank.setId(id);
                fileBank.setFileName(file.getName());
                fileBank.setTableName("biz_protection_material");
                fileBank.setFileBlob(fileToBytes);
                iFileBankService.saveOrUpdate(fileBank);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}