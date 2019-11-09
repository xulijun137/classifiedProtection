package com.jwell.classifiedProtection.commons.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.jwell.classifiedProtection.entry.ProtectionMaterial;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Slf4j
public class PDFUtils {

    public static byte[] createPDFDoc(ProtectionMaterial protectionInfo) {

        try {
            //创建文件
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //建立一个书写器
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            //PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            //打开文件
            document.open();

            //从resources资源文件夹里获取项目自带的字体
            BaseFont bfChinese = BaseFont.createFont("/ttc/msyh.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //红色字体
            Font myfont = new Font(bfChinese);
            //greenFont.setColor(BaseColor.RED);

            //创建章节
            Paragraph title = new Paragraph(protectionInfo.getTitle(), myfont);
            //title.setLeading(4f);
            title.setAlignment(Element.ALIGN_CENTER);

            Chunk chunkType = new Chunk(protectionInfo.getType(), myfont);
            Chunk chunkAuthor = new Chunk(protectionInfo.getAuthor());
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String localTime = df.format(protectionInfo.getCreateTime());
            Chunk chunkTime = new Chunk(localTime, myfont);

            //类型和时间
            Paragraph phrase = new Paragraph();
            //设置上方行距
            phrase.setLeading(24.5f);
            phrase.setAlignment(Element.ALIGN_CENTER);
            phrase.add(chunkType);
            phrase.add("    ");
            phrase.add(chunkAuthor);
            phrase.add("    ");
            phrase.add(chunkTime);

            //摘要
            Paragraph abstractsParagraph = new Paragraph(protectionInfo.getAbstracts(), myfont);
            abstractsParagraph.setFirstLineIndent(24);

            //将章节添加到文章中
            document.add(title);
            document.add(phrase);
            document.add(abstractsParagraph);

            //关闭文档
            document.close();
            //关闭书写器
            writer.close();

            log.info("生成PDF文档成功");
            return baos.toByteArray();

        } catch (Exception e) {
            log.info("生成PDF文档失败：" + e.getMessage());
            return null;
        }

    }
}
