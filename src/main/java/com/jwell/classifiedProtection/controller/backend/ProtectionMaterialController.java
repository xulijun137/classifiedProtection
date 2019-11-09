package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.entry.ProtectionMaterial;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.jwell.classifiedProtection.service.IProtectionMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 等保资料 前端控制器
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-05
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "等保资料接口", tags = "等保资料接口")
@RestController
@RequestMapping("/backend/protection-material")
public class ProtectionMaterialController extends BaseController {

    @Autowired
    private IProtectionMaterialService iProtectionMaterialService;

    @Autowired
    private IFileBankService iFileBankService;

    /**
     * 等保资料分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "等保资料分页列表", notes = "获取等保资料分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "title", value = "标题", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "author", value = "作者", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "时间", dataType = "LocalDateTime", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getProtectionMaterialList(String title, String author, String type,
                                                  @RequestParam(value = "createTime", required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createTime,
                                                  Integer pageNum, Integer pageSize) {

        ResultObject resultObject = new ResultObject();

        try {
            QueryWrapper<ProtectionMaterial> protectionMaterialQueryWrapper = new QueryWrapper<>();
            protectionMaterialQueryWrapper.lambda().orderByDesc(ProtectionMaterial::getCreateTime);

            if (!StringUtils.isEmpty(title)) {
                protectionMaterialQueryWrapper.lambda().like(ProtectionMaterial::getTitle, title);
            }

            if (!StringUtils.isEmpty(author)) {
                protectionMaterialQueryWrapper.lambda().like(ProtectionMaterial::getAuthor, author);
            }

            if (!StringUtils.isEmpty(type)) {
                protectionMaterialQueryWrapper.lambda().eq(ProtectionMaterial::getType, type);
            }

            if (createTime != null) {
                protectionMaterialQueryWrapper.lambda().eq(ProtectionMaterial::getCreateTime, createTime);
            }


            IPage<ProtectionMaterial> ProtectionMaterialIPage = iProtectionMaterialService.paging(pageNum, pageSize, protectionMaterialQueryWrapper);
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(ProtectionMaterialIPage);
        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询等保资料详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询等保资料", notes = "查询等保资料详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "等保资料ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Serializable id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {
                Map dataMap = new HashMap<>();
                ProtectionMaterial protectionMaterial = iProtectionMaterialService.getById(id);
                dataMap.put("protectionMaterial", protectionMaterial);

                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_protection_material")
                        .eq(FileBank::getForeignId, protectionMaterial.getId());
                List<FileBank> fileBankList = iFileBankService.list(fileBankQueryWrapper);
                dataMap.put("fileBankList", fileBankList);

                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(dataMap);

            } else {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("参数不能为空");
            }

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultObject.setMsg("查询失败");
            e.printStackTrace();
        }

        return resultObject;
    }

    @ApiOperation(value = "等保资料新增或者修改", notes = "等保资料新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(ProtectionMaterial protectionMaterial, MultipartFile[] multipartFiles) {

        ResultObject resultObject = ResultObject.failure();

        try {

            boolean b = iProtectionMaterialService.saveOrUpdate(protectionMaterial);

            //处理上传的文件
            if (multipartFiles.length != 0) {
                for (MultipartFile file : multipartFiles) {
                    FileBank fileBank = new FileBank();
                    fileBank.setForeignId(protectionMaterial.getId());
                    fileBank.setFileBlob(file.getBytes());
                    fileBank.setFileName(file.getOriginalFilename());
                    fileBank.setTableName("biz_protection_material");
                    fileBank.setModuleName("等保资料管理");
                    fileBank.setFileType("等保资料附件");
                    boolean flag = iFileBankService.saveOrUpdate(fileBank);
                }
            }

            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 等保资料删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "等保资料删除", notes = "等保资料删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = ResultObject.failure();

        try {
            Boolean b = iProtectionMaterialService.removeById(id);
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }

    @ApiOperation(value = "导出生成的PDF文档", notes = "导出PDF文档")
    @RequestMapping(value = "/export_pdf", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "等保资料id"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResponseEntity<byte[]> pdfDownload(Integer id) {

        try {
            ProtectionMaterial protectionMaterial = iProtectionMaterialService.getDetailById(id);
            HttpHeaders headers = new HttpHeaders();
            String title = null;
            try {
                //中文乱码
                title = new String(protectionMaterial.getTitle()
                        .getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //设置下载的pdf文件名全称(代后缀)
            headers.setContentDispositionFormData("attachment", title + ".pdf");
            //设置返回下载的文件类型(pdf/vnd.ms-excel等)
            headers.setContentType(MediaType.APPLICATION_PDF);

            byte[] bytes = this.createPDFDoc(protectionMaterial);
            log.info("导出PDF成功");
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            log.info("导出PDF失败：" + e.getMessage());
            return null;
        }

    }

    private byte[] createPDFDoc(ProtectionMaterial protectionInfo) {

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
