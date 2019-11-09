package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.entry.SecurityTool;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.jwell.classifiedProtection.service.ISecurityToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 安全技术工具 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "安全技术工具接口集合", tags = "安全技术工具接口集合")
@RestController
@RequestMapping("/backend/security-tool")
public class SecurityToolController extends BaseController {

    @Autowired
    private ISecurityToolService iSecurityToolService;

    @Autowired
    private IFileBankService iFileBankService;

    /**
     * 安全工具分页列表
     *
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "安全工具分页列表", notes = "安全工具分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "toolName", value = "工具名", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "fileName", value = "附件名", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "时间", dataType = "LocalDateTime", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject paging(String toolName, String fileName, String type,
                               @RequestParam(value = "createTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createTime,
                               Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.failure();

        try {
            QueryWrapper<SecurityTool> securityToolQueryWrapper = new QueryWrapper<>();
            securityToolQueryWrapper.lambda().orderByDesc(SecurityTool::getCreateTime);

            if (!StringUtils.isEmpty(toolName)) {
                securityToolQueryWrapper.lambda().like(SecurityTool::getToolName, toolName);
            }
            if (!StringUtils.isEmpty(fileName)) {
                securityToolQueryWrapper.lambda().like(SecurityTool::getFileName, fileName);
            }
            if (!StringUtils.isEmpty(type)) {
                securityToolQueryWrapper.lambda().eq(SecurityTool::getType, type);
            }
            if (!StringUtils.isEmpty(createTime)) {
                LocalDateTime localMin = LocalDateTime.of(createTime.toLocalDate(), LocalTime.MIN);
                LocalDateTime localMax = LocalDateTime.of(createTime.toLocalDate(), LocalTime.MAX);
                securityToolQueryWrapper.lambda().between(SecurityTool::getCreateTime, localMin, localMax);
            }

            IPage<SecurityTool> iPage = new Page<>(pageNum, pageSize);
            IPage<SecurityTool> securityToolIPage = iSecurityToolService.page(iPage, securityToolQueryWrapper);
            for (SecurityTool securityTool : securityToolIPage.getRecords()) {
                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().orderByDesc(FileBank::getCreateTime);
                fileBankQueryWrapper.lambda().eq(FileBank::getForeignId, securityTool.getId())
                        .eq(FileBank::getTableName, "biz_security_tool");
                List<FileBank> fileBankList = iFileBankService.list(fileBankQueryWrapper);
                if (!fileBankList.isEmpty()) {
                    securityTool.setFileName(fileBankList.get(0).getFileName());
                }
            }
            resultObject = ResultObject.success();
            resultObject.setData(securityToolIPage);
        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 安全工具详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "安全工具详情", notes = "安全工具详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "主键id", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Integer id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {
                Map dataMap = new HashMap<>();
                SecurityTool securityTool = iSecurityToolService.getById(id);
                dataMap.put("securityTool", securityTool);

                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_security_tool")
                        .eq(FileBank::getForeignId, securityTool.getId());
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

    @ApiOperation(value = "安全工具新增或者修改", notes = "安全工具新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(SecurityTool securityTool, MultipartFile[] multipartFiles) {

        ResultObject resultObject = ResultObject.failure();

        try {

            boolean b = iSecurityToolService.saveOrUpdate(securityTool);
            //处理上传的文件
            if (multipartFiles.length != 0) {
                for (MultipartFile file : multipartFiles) {
                    FileBank fileBank = new FileBank();
                    fileBank.setForeignId(securityTool.getId());
                    fileBank.setFileBlob(file.getBytes());
                    fileBank.setFileName(file.getOriginalFilename());
                    fileBank.setTableName("biz_security_tool");
                    fileBank.setModuleName("安全技术工具管理");
                    fileBank.setFileType("安全技术工具附件");
                    boolean flag = iFileBankService.saveOrUpdate(fileBank);
                }
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
     * 安全工具删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "安全工具删除", notes = "安全工具删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = ResultObject.failure();

        try {
            Boolean aBoolean = iSecurityToolService.removeById(id);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

}
