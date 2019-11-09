package com.jwell.classifiedProtection.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.enums.TaskProgressEnum;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.entry.Organization;
import com.jwell.classifiedProtection.entry.vo.OrganizationDetailVo;
import com.jwell.classifiedProtection.entry.vo.OrganizationPagingVo;
import com.jwell.classifiedProtection.service.IAssessTaskService;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.jwell.classifiedProtection.service.IOrganizationService;
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
 * 单位组织 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-22
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "单位组织接口集合", tags = "单位组织接口集合")
@RestController
@RequestMapping("/app/organization")
public class OrganizationAppController extends BaseController {

    @Autowired
    private IAssessTaskService iAssessTaskService;

    @Autowired
    private IOrganizationService iOrganizationService;

    @Autowired
    private IFileBankService iFileBankService;

    /**
     * 返回所有单位组织列表
     *
     * @return
     */
    @ApiOperation(value = "所有单位组织列表", notes = "所有单位组织列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject listAll() {

        ResultObject resultObject = ResultObject.failure();
        try {
            List<Organization> systemList = iOrganizationService.list();
            resultObject = ResultObject.success();
            resultObject.setData(systemList);
        } catch (Exception e) {
        }
        return resultObject;
    }

    /**
     * 单位组织单位分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "单位组织单位分页列表", notes = "单位组织单位分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "公司名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getOrganizationList(String name, Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.failure();

        try {

            Organization organization = new Organization();
            organization.setOrganizationName(name);

            IPage<OrganizationPagingVo> iPage = null;
            if (pageNum == null || pageSize == null) {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("页长或页码不能为空");
                return resultObject;
            } else {
                iPage = new Page(pageNum, pageSize);
            }
            IPage<OrganizationPagingVo> OrganizationVoIPage = iOrganizationService.selectOrganizationVoPaging(iPage, organization);
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(OrganizationVoIPage);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 单位组织单位详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "单位组织单位详情", notes = "查看单位组织单位详情")
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

                Organization organization = iOrganizationService.getById(id);
                OrganizationDetailVo organizationDetailVo = new OrganizationDetailVo();
                BeanUtils.copyProperties(organization, organizationDetailVo);

                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_organization")
                        .eq(FileBank::getForeignId, organization.getId());
                List<FileBank> fileBankList = iFileBankService.list(fileBankQueryWrapper);
                if (!fileBankList.isEmpty()) {
                    for (FileBank fileBank : fileBankList) {
                        if (fileBank.getFileType().equals("营业执照")) {
                            organizationDetailVo.setBusinessLicenseId(fileBank.getId());
                            continue;
                        }

                        if (fileBank.getFileType().equals("负责人证件")) {
                            organizationDetailVo.setChargerLicenseId(fileBank.getId());
                            continue;
                        }

                        if (fileBank.getFileType().equals("安全员证书")) {
                            organizationDetailVo.setOfficerLicenseId(fileBank.getId());
                            continue;
                        }
                    }
                }
                resultObject = ResultObject.success();
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(organizationDetailVo);
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

    @ApiOperation(value = "单位组织单位新增或者修改", notes = "单位组织单位新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(Organization organization,
                                     @RequestParam(name = "taskId") Integer taskId,
                                     MultipartFile businessLicenseFile,
                                     MultipartFile chargerLicenseFile,
                                     MultipartFile officerLicenseFile) {

        ResultObject resultObject = ResultObject.failure();

        try {
            boolean b = iOrganizationService.saveOrUpdate(organization);

            byte[] businessLicenseByte = null;
            byte[] chargerLicenseByte = null;
            byte[] officerLicenseByte = null;

            boolean flag1 = false, flag2 = false, flag3 = false;
            if (businessLicenseFile != null) {
                businessLicenseByte = businessLicenseFile.getBytes();

                FileBank fileBank = new FileBank();
                fileBank.setFileName(businessLicenseFile.getOriginalFilename());
                fileBank.setFileBlob(businessLicenseByte);

                fileBank.setModuleName("单位组织单位管理");
                fileBank.setTableName("biz_organization");
                fileBank.setForeignId(organization.getId());
                fileBank.setFileType("营业执照");

                flag1 = iFileBankService.saveOrUpdate(fileBank);
            }

            if (chargerLicenseFile != null) {
                chargerLicenseByte = chargerLicenseFile.getBytes();

                FileBank fileBank = new FileBank();
                fileBank.setFileName(chargerLicenseFile.getOriginalFilename());
                fileBank.setFileBlob(chargerLicenseByte);

                fileBank.setModuleName("单位组织单位管理");
                fileBank.setTableName("biz_organization");
                fileBank.setForeignId(organization.getId());
                fileBank.setFileType("负责人证件");

                flag2 = iFileBankService.saveOrUpdate(fileBank);
            }

            if (officerLicenseFile != null) {
                officerLicenseByte = officerLicenseFile.getBytes();

                FileBank fileBank = new FileBank();
                fileBank.setFileName(officerLicenseFile.getOriginalFilename());
                fileBank.setFileBlob(officerLicenseByte);

                fileBank.setModuleName("单位组织单位管理");
                fileBank.setTableName("biz_organization");
                fileBank.setForeignId(organization.getId());
                fileBank.setFileType("安全员证书");

                flag3 = iFileBankService.saveOrUpdate(fileBank);
            }

            if (flag1 && flag2 && flag3) {
                AssessTask assessTask = new AssessTask();
                assessTask.setId(taskId);
                assessTask.setProgressState(TaskProgressEnum.ORGANIZATION_INFO.getValue());
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

    /**
     * 删除单位组织单位
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除单位组织单位", notes = "删除单位组织单位")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = ResultObject.failure();

        try {

            Boolean b = iOrganizationService.removeById(id);
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }

    @ApiOperation(value = "预览或者展示图片", notes = "预览或者展示图片")
    @RequestMapping(value = "/showImage", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "文件ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResponseEntity showImage(Integer id) throws Exception {

        try {
            FileBank fileBank = iFileBankService.getById(id);

            HttpHeaders headers = new HttpHeaders();// 设置一个head
            headers.setContentType(MediaType.IMAGE_JPEG);// 内容是字节流
            headers.setPragma("no-cache");
            headers.setExpires(0);
            // 开始下载
            return new ResponseEntity<byte[]>(fileBank.getFileBlob(), headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @ApiOperation(value = "删除图片", notes = "删除图片")
    @RequestMapping(value = "/deleteImage", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "文件ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteImage(Integer id) throws Exception {

        ResultObject resultObject = ResultObject.failure();
        try {
            Boolean aBoolean = iFileBankService.removeById(id);
            resultObject = ResultObject.success();
            return resultObject;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultObject;

    }
}
