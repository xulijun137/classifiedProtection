package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.utils.ZipDownloadUtils;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.ArchiveMaterialList;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.service.IArchiveMaterialListService;
import com.jwell.classifiedProtection.service.IFileBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 材料审核详情 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "材料审核详情接口", tags = "材料审核详情接口")
@RestController
@RequestMapping("/backend/material-audit-list")
public class ArchiveMaterialListController extends BaseController {

    @Autowired
    private IArchiveMaterialListService iArchiveMaterialListService;

    @Autowired
    private IFileBankService iFileBankService;

    /**
     * 整改建议详情列表分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "整改建议详情列表分页列表", notes = "获取整改建议详情列表分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "auditId", value = "审核ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getMaterialExamineListList(
            @RequestParam(name = "auditId", required = true) Integer auditId,
            Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.failure();

        try {
            resultObject = ResultObject.success();
            resultObject.setData(null);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询整改建议详情列表详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询整改建议详情列表", notes = "查询整改建议详情列表详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "整改建议详情列表ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Serializable id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {

                ArchiveMaterialList ArchiveMaterialList = iArchiveMaterialListService.getDetailById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(ArchiveMaterialList);
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

    @ApiOperation(value = "上传备案材料", notes = "上传备案材料")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestParam(name = "archiveMaterialList") ArchiveMaterialList archiveMaterialList,
                                     @RequestParam(name = "multipartFile") MultipartFile multipartFile) {

        ResultObject resultObject = ResultObject.failure();

        try {
            archiveMaterialList.setFileName(multipartFile.getOriginalFilename());
            boolean b = iArchiveMaterialListService.saveOrUpdate(archiveMaterialList);
            if (b) {
                FileBank fileBank = new FileBank();
                fileBank.setModuleName("备案材料——后端");
                fileBank.setTableName("biz_archive_material_list");
                fileBank.setForeignId(archiveMaterialList.getId());
                fileBank.setFileType(archiveMaterialList.getMaterialType());
                fileBank.setFileBlob(multipartFile.getBytes());
                iFileBankService.saveOrUpdate(fileBank);
            }
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 下载单个审核材料
     *
     * @param id
     * @param id
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "下载单个审核材料", notes = "下载单个审核材料")
    @RequestMapping(value = "/downloadOne", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResponseEntity download(Integer id) {

        try {
            if (id != null) {
                //MaterialAuditList materialAuditList = iMaterialAuditListService.getDetailById(id);
                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().orderByDesc(FileBank::getCreateTime);
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_archive_material_list")
                        .eq(FileBank::getForeignId, id);
                FileBank fileBank = iFileBankService.getOne(fileBankQueryWrapper);
                if (fileBank != null) {

                    HttpHeaders headers = new HttpHeaders();// 设置一个head
                    //居然是GBK，严重怀疑是浏览器默认编码是GBK导致的！！！
                    headers.setContentDispositionFormData("attachment",
                            new String(fileBank.getFileName().getBytes("GBK"), "ISO8859-1"));
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);// 内容是字节流
                    headers.setPragma("no-cache");
                    headers.setExpires(0);
                    return new ResponseEntity<>(fileBank.getFileBlob(), headers, HttpStatus.OK);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * downloadMultiple
     *
     * @param materialType
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "下载多个审核材料", notes = "下载多个审核材料")
    @RequestMapping(value = "/downloadMultiple", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "materialType", value = "1=下载备案材料，2=下载所有", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResponseEntity<byte[]> downloadMultiple(Integer materialType) throws Exception {

        if (materialType != null) {
            //批量下载【备案材料】
            if (materialType == 1) {
                List<FileBank> dataList = new ArrayList<>();
                QueryWrapper<ArchiveMaterialList> materialAuditListQueryWrapper = new QueryWrapper<>();
                materialAuditListQueryWrapper.lambda().eq(ArchiveMaterialList::getMaterialType, "备案材料");
                List<ArchiveMaterialList> archiveMaterialLists = iArchiveMaterialListService.list(materialAuditListQueryWrapper);
                for (ArchiveMaterialList archiveMaterialList : archiveMaterialLists) {

                    QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                    fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_archive_material_list")
                            .eq(FileBank::getForeignId, archiveMaterialList.getId());
                    List<FileBank> fileBankList = iFileBankService.list(fileBankQueryWrapper);
                    dataList.addAll(fileBankList);

                }
                //压缩文件
                ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();
                ZipDownloadUtils.batchFileToZIP(dataList, byteOutPutStream);

                //下载文件
                String fileName = "批量下载【备案材料】.zip";
                return ZipDownloadUtils.downloadZIP(fileName, byteOutPutStream);

            }

            //下载所有
            if (materialType == 2) {
                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_archive_material_list");
                List<FileBank> fileBankList = iFileBankService.list(fileBankQueryWrapper);
                //压缩文件
                ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();
                ZipDownloadUtils.batchFileToZIP(fileBankList, byteOutPutStream);

                //下载文件
                String fileName = "下载所有.zip";
                return ZipDownloadUtils.downloadZIP(fileName, byteOutPutStream);
            }
        }
        return null;
    }

}
