package com.jwell.classifiedProtection.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.enums.TaskProgressEnum;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.entry.SystemGrading;
import com.jwell.classifiedProtection.entry.vo.SystemGradingVo;
import com.jwell.classifiedProtection.service.IAssessTaskService;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.jwell.classifiedProtection.service.ISystemGradingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
import java.util.List;

/**
 * <p>
 * 系统定级 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-28
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "系统定级接口", tags = "系统定级接口")
@RestController
@RequestMapping("/app/system-grading")
public class SystemGradingAppController extends BaseController {

    @Autowired
    private IAssessTaskService iAssessTaskService;

    @Autowired
    private ISystemGradingService iSystemGradingService;

    @Autowired
    private IFileBankService iFileBankService;

    @ApiOperation(value = "系统定级新增或者修改", notes = "系统定级新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(SystemGrading SystemGradingGrading,
                                     @RequestParam(name = "taskId") Integer taskId,
                                     @RequestParam(name = "expertFile", required = false) MultipartFile expertFile,
                                     @RequestParam(name = "departmentFile", required = false) MultipartFile departmentFile,
                                     @RequestParam(name = "gradingFile", required = false) MultipartFile gradingFile) {

        ResultObject resultObject = ResultObject.failure();

        try {
            boolean b = iSystemGradingService.saveOrUpdate(SystemGradingGrading);

            byte[] expertFileByte = null;
            byte[] departmentFileByte = null;
            byte[] gradingFileByte = null;

            boolean flag1 = false, flag2 = false, flag3 = false;
            if (expertFile != null) {
                expertFileByte = expertFile.getBytes();

                FileBank fileBank = new FileBank();
                fileBank.setFileName(expertFile.getOriginalFilename());
                fileBank.setFileBlob(expertFileByte);

                fileBank.setModuleName("系统定级");
                fileBank.setTableName("biz_system_grading");
                fileBank.setForeignId(SystemGradingGrading.getId());
                fileBank.setFileType("专家评审情况");

                flag1 = iFileBankService.saveOrUpdate(fileBank);
            }

            if (departmentFileByte != null) {
                departmentFileByte = departmentFile.getBytes();

                FileBank fileBank = new FileBank();
                fileBank.setFileName(departmentFile.getOriginalFilename());
                fileBank.setFileBlob(departmentFileByte);

                fileBank.setModuleName("系统定级");
                fileBank.setTableName("biz_system_grading");
                fileBank.setForeignId(SystemGradingGrading.getId());
                fileBank.setFileType("主管部门审批定级情况");

                flag2 = iFileBankService.saveOrUpdate(fileBank);
            }

            if (gradingFileByte != null) {
                gradingFileByte = gradingFile.getBytes();

                FileBank fileBank = new FileBank();
                fileBank.setFileName(gradingFile.getOriginalFilename());
                fileBank.setFileBlob(gradingFileByte);

                fileBank.setModuleName("系统定级");
                fileBank.setTableName("biz_system_grading");
                fileBank.setForeignId(SystemGradingGrading.getId());
                fileBank.setFileType("系统定级报告");

                flag3 = iFileBankService.saveOrUpdate(fileBank);
            }

            if (flag1 && flag2 && flag3) {
                AssessTask assessTask = new AssessTask();
                assessTask.setId(taskId);
                assessTask.setProgressState(TaskProgressEnum.ARCHIVE_MATERIAL.getValue());
                iAssessTaskService.updateById(assessTask);
            }

            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    @ApiOperation(value = "系统定级详情", notes = "系统定级详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "主键ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Integer id) {

        ResultObject resultObject = ResultObject.failure();
        try {

            if (id != null) {

                SystemGradingVo systemGradingVo = new SystemGradingVo();
                SystemGrading systemGrading = iSystemGradingService.getById(id);
                BeanUtils.copyProperties(systemGrading, systemGradingVo);

                //查询文件
                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_system_grading")
                        .eq(FileBank::getForeignId, systemGrading.getId());
                List<FileBank> fileBankList = iFileBankService.list(fileBankQueryWrapper);
                if (!fileBankList.isEmpty()) {
                    for (FileBank fileBank : fileBankList) {
                        if (fileBank.getFileType().equals("专家评审情况")) {
                            systemGradingVo.setExpertFileName(fileBank.getFileName());
                            systemGradingVo.setExpertFileId(fileBank.getId());
                            continue;
                        }

                        if (fileBank.getFileType().equals("主管部门审批定级情况")) {
                            systemGradingVo.setDepartmentFileName(fileBank.getFileName());
                            systemGradingVo.setDepartmentFileId(fileBank.getId());
                            continue;
                        }

                        if (fileBank.getFileType().equals("系统定级报告")) {
                            systemGradingVo.setGradingFileName(fileBank.getFileName());
                            systemGradingVo.setGradingFileId(fileBank.getId());
                            continue;
                        }
                    }
                }

                resultObject = ResultObject.success();
                resultObject.setData(systemGradingVo);

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


    @ApiOperation(value = "下载文件", notes = "下载文件")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "附件ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResponseEntity download(Integer id) throws Exception {

        try {

            FileBank fileBank = iFileBankService.getById(id);

            HttpHeaders headers = new HttpHeaders();// 设置一个head

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);// 内容是字节流
            headers.setContentDispositionFormData("attachment",
                    new String(fileBank.getFileName().getBytes("GBK"), "ISO8859-1"));

            headers.setPragma("no-cache");
            headers.setExpires(0);
            // 开始下载
            return new ResponseEntity<byte[]>(fileBank.getFileBlob(), headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
}
