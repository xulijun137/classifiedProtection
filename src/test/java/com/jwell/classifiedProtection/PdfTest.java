package com.jwell.classifiedProtection;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClassifiedProtectionApplication.class)
public class PdfTest {

    private static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    IUserService iUserService;

    @Resource
    RedisUtil redisUtil;

    @Test
    public void createTablePDF() throws IOException {

        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String filename = "C:/pdfTable"+ milliSecond +".pdf";

        List<User> userList = iUserService.list();
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.addTitle("example of PDF");
            document.open();
            PdfPTable table = createTable(writer,userList);
            document.add(table);
            logger.warn("打印完成");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public static PdfPTable createTable(PdfWriter writer, List<User> userList)
            throws IOException, DocumentException {

        PdfPTable table = new PdfPTable(3);//生成一个三列的表格
        PdfPCell cell;
        int size = 20;
//        Font font = new Font(BaseFont.createFont("C://Windows//Fonts//simfang.ttf", BaseFont.IDENTITY_H,
//                BaseFont.NOT_EMBEDDED));
        for(User user:userList) {
            cell = new PdfPCell(new Phrase(user.getUserName()));//产品编号
            cell.setFixedHeight(size);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(user.getPassword()));//产品名称
            cell.setFixedHeight(size);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(user.getPhone()));//产品价格
            cell.setFixedHeight(size);
            table.addCell(cell);
        }
        return table;
    }

    @Test
    public void test6() throws DocumentException, IOException {
        //创建文件
        Document document = new Document();
        //建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/pdf/test6.pdf"));
        //打开文件
        document.open();

        //中文字体,解决中文不能显示问题
//        BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        //这里用项目中的字体库，防止字体库不存在发生异常
        BaseFont bfChinese = BaseFont.createFont("/ttc/msyh.ttc,1", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

        //红色字体
        Font greenFont = new Font(bfChinese);
        greenFont.setColor(BaseColor.RED);
        //创建章节
        Paragraph chapterTitle = new Paragraph("段落标题Ronnie O'Sullivan", greenFont);
//        Chapter chapter1 = new Chapter(chapterTitle, 1);
//        chapter1.setNumberDepth(0);
//
//        Paragraph sectionTitle = new Paragraph("部分标题", greenFont);
//        Section section1 = chapter1.addSection(sectionTitle);
//
//        Paragraph sectionContent = new Paragraph("部分内容", blueFont);
//        section1.add(sectionContent);

        //将章节添加到文章中
        document.add(chapterTitle);

        //关闭文档
        document.close();
        //关闭书写器
        writer.close();
    }
}
