package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.SecurityTemplate;
import com.jwell.classifiedProtection.service.ISecurityTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/backend/security-template")
public class SecurityTemplateController extends BaseController {

    @Autowired
    private ISecurityTemplateService iSecurityTemplateService;

    /**
     * 安全制度模板分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "安全制度模板分页列表", notes = "安全制度模板分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键字", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getSecurityTemplateList(String keyword,Integer pageNum, Integer pageSize) {

        ResultObject resultObject = new ResultObject();

        try {
            QueryWrapper<SecurityTemplate> SecurityTemplateQueryWrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty(keyword)){
                SecurityTemplateQueryWrapper.lambda().eq(SecurityTemplate::getTitle,keyword);
            }
            IPage<SecurityTemplate> SecurityTemplateIPage = iSecurityTemplateService.paging(pageNum,pageSize,
                    SecurityTemplateQueryWrapper);
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(SecurityTemplateIPage);
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

                SecurityTemplate SecurityTemplate = iSecurityTemplateService.getDetailById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(SecurityTemplate);
            }else{
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

    @ApiOperation(value = "安全模板新增或者修改", notes = "安全模板新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(SecurityTemplate securityTemplate, MultipartFile file) {

        ResultObject resultObject = ResultObject.failure();

        try {

            boolean b = iSecurityTemplateService.save(securityTemplate);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }


    /**
     * 安全制度模板删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "安全制度模板删除", notes = "删除安全制度模板")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "模板主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = new ResultObject();

        try {
            Boolean aBoolean = iSecurityTemplateService.removeById(id);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }
}
