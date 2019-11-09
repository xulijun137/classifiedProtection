package com.jwell.classifiedProtection.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.enums.AuditStateEnum;
import com.jwell.classifiedProtection.commons.enums.FinishStateEnum;
import com.jwell.classifiedProtection.commons.enums.TableNameEnum;
import com.jwell.classifiedProtection.commons.enums.TaskProgressEnum;
import com.jwell.classifiedProtection.commons.utils.ZipDownloadUtils;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.ArchiveMaterialList;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.service.IArchiveMaterialListService;
import com.jwell.classifiedProtection.service.IAssessTaskService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 材料审核 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "备案材料接口", tags = "备案材料接口")
@RestController
@RequestMapping("/app/archive-material")
public class ArchiveMaterialAppController extends BaseController {

    @Autowired
    private IArchiveMaterialListService iArchiveMaterialListService;

    @Autowired
    private IFileBankService iFileBankService;

    @Autowired
    private IAssessTaskService iAssessTaskService;

    /**
     * 备案材料分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "备案材料分页列表", notes = "备案材料分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getMaterialExamineListList(
            Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.failure();

        try {
            IPage iPage = new Page(pageNum, pageSize);
            IPage<ArchiveMaterialList> MaterialExamineListIPage = iArchiveMaterialListService.pagingVo(iPage, null);
            resultObject = ResultObject.success();
            resultObject.setData(MaterialExamineListIPage);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
    public ResultObject saveOrUpdate(ArchiveMaterialList archiveMaterialList,
                                     @RequestParam(name = "multipartFile") MultipartFile multipartFile) {

        ResultObject resultObject = ResultObject.failure();

        try {
            archiveMaterialList.setFileName(multipartFile.getOriginalFilename());

            archiveMaterialList.setAuditState(AuditStateEnum.AUDIT_NOT_PASSED.getValue());
            archiveMaterialList.setFinishState(FinishStateEnum.NOT_FINISHED.getValue());

            boolean b = iArchiveMaterialListService.saveOrUpdate(archiveMaterialList);
            if (b) {
                FileBank fileBank = new FileBank();
                fileBank.setModuleName("备案材料-前端");
                fileBank.setTableName(TableNameEnum.BIZ_ARCHIVE_MATERIAL_LIST.getValue());
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
     * 下载单个备案材料
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "下载单个备案材料", notes = "下载单个备案材料")
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
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, TableNameEnum.BIZ_ARCHIVE_MATERIAL_LIST.getValue())
                        .eq(FileBank::getForeignId, id);
                List<FileBank> fileBankList = iFileBankService.list(fileBankQueryWrapper);
                if (!fileBankList.isEmpty()) {
                    FileBank fileBank = fileBankList.get(0);

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
    @ApiOperation(value = "下载多个备案材料", notes = "下载多个备案材料")
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
                    fileBankQueryWrapper.lambda().eq(FileBank::getTableName, TableNameEnum.BIZ_ARCHIVE_MATERIAL_LIST.getValue())
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

    /**
     * 删除备案材料
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除备案材料", notes = "删除备案材料")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = ResultObject.failure();

        try {

            ArchiveMaterialList archiveMaterialList = iArchiveMaterialListService.getById(id);
            QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
            fileBankQueryWrapper.lambda().eq(FileBank::getTableName, TableNameEnum.BIZ_ARCHIVE_MATERIAL_LIST.getValue())
                    .eq(FileBank::getModuleName, "备案材料-前端").eq(FileBank::getForeignId, archiveMaterialList.getId());
            boolean b1 = iFileBankService.remove(fileBankQueryWrapper);

            boolean b2 = iArchiveMaterialListService.removeById(id);

            if (b1 && b2) {
                resultObject = ResultObject.success();
                return resultObject;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }

    @ApiOperation(value = "备案材料下一步", notes = "备案材料下一步")
    @RequestMapping(value = "/next", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "taskId", value = "评测任务ID"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "mterialListId", value = "备案材料ID"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject next(Integer taskId, Integer mterialListId) throws Exception {

        ResultObject resultObject = ResultObject.failure();
        try {
            AssessTask assessTask = new AssessTask();
            assessTask.setId(taskId);
            assessTask.setMaterialListId(mterialListId);
            assessTask.setProgressState(TaskProgressEnum.RELATE_ASSET.getValue());
            boolean b = iAssessTaskService.saveOrUpdate(assessTask);
            resultObject = ResultObject.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;
    }
}
