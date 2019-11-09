package com.jwell.classifiedProtection.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.entry.SecurityTemplate;
import com.jwell.classifiedProtection.entry.vo.SecurityTemplateVo;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.jwell.classifiedProtection.service.ISecurityTemplateService;
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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 安全制度模板 前端控制器
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-07
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "安全制度模板", tags = "安全制度模板")
@RestController
@RequestMapping("/app/security-template")
public class SecurityTemplateAppController extends BaseController {

    @Autowired
    private ISecurityTemplateService iSecurityTemplateService;

    @Autowired
    private IFileBankService iFileBankService;

    /**
     * 安全制度模板分页列表
     *
     * @return
     */
    @ApiOperation(value = "安全制度模板分页列表", notes = "安全制度模板分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getSecurityTemplateList() {

        ResultObject resultObject = ResultObject.failure();

        try {

            List<SecurityTemplate> securityTemplateList = iSecurityTemplateService.list();
            List<SecurityTemplateVo> securityTemplateVoList = new ArrayList<>();
            for (SecurityTemplate securityTemplate : securityTemplateList) {

                SecurityTemplateVo securityTemplateVo = new SecurityTemplateVo();
                BeanUtils.copyProperties(securityTemplate, securityTemplateVo);

                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().eq(FileBank::getForeignId, securityTemplate.getId())
                        .eq(FileBank::getTableName, "biz_security_template");
                FileBank fileBank = iFileBankService.getOne(fileBankQueryWrapper);
                if (fileBank != null) {
                    securityTemplateVo.setFileBank(fileBank);
                }

                securityTemplateVoList.add(securityTemplateVo);
            }
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(securityTemplateVoList);
        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询安全制度模板详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询安全制度模板", notes = "查询安全制度模板详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "安全制度模板ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Integer id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {
                Map dataMap = new HashMap<>();
                SecurityTemplate securityTemplate = iSecurityTemplateService.getDetailById(id);
                dataMap.put("securityTemplate", securityTemplate);

                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_security_template")
                        .eq(FileBank::getForeignId, securityTemplate.getId());
                FileBank fileBankList = iFileBankService.getOne(fileBankQueryWrapper);
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

    @ApiOperation(value = "下载安全制度模板", notes = "下载安全制度模板")
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

    /**
     * 安全制度模板删除
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "安全制度模板删除", notes = "安全制度模板删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = ResultObject.failure();

        try {
            SecurityTemplate securityTemplate = iSecurityTemplateService.getById(id);
            if (securityTemplate != null) {
                Boolean flag = iFileBankService.removeById(securityTemplate.getId());
                Boolean aBoolean = iSecurityTemplateService.removeById(id);
            }
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }
}
