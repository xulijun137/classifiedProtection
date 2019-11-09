package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.Enterprise;
import com.jwell.classifiedProtection.entry.vo.EnterpriseVo;
import com.jwell.classifiedProtection.service.IEnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 企业 前端控制器
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "企业接口集合", tags = "企业接口集合")
@RestController
@RequestMapping("/backend/enterprise")
public class EnterpriseController extends BaseController {

    @Autowired
    private IEnterpriseService iEnterpriseService;

    /**
     * 返回所有企业列表
     *
     * @return
     */
    @ApiOperation(value = "所有企业列表", notes = "所有企业列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationAdviceTaskList() {

        ResultObject resultObject = ResultObject.failure();
        try {
            List<Enterprise> systemList = iEnterpriseService.list();
            resultObject.setData(systemList);
            resultObject = ResultObject.success();

        } catch (Exception e) {
        }
        return resultObject;
    }

    /**
     * 企业分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "企业分页列表", notes = "企业分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "公司名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "时间", dataType = "LocalDateTime", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getEnterpriseList(String name,
                                          @RequestParam(value = "createTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createTime,
                                          Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.failure();

        try {

            Enterprise enterprise = new Enterprise();
            enterprise.setEnterpriseName(name);
            enterprise.setCreateTime(createTime);

            IPage<EnterpriseVo> iPage = null;
            if (pageNum == null || pageSize == null) {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("页长或页码不能为空");
                return resultObject;
            } else {
                iPage = new Page(pageNum, pageSize);
            }
            IPage<EnterpriseVo> enterpriseVoIPage = iEnterpriseService.selectEnterpriseVoPaging(iPage, enterprise);
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(enterpriseVoIPage);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 企业详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "企业详情", notes = "查看企业详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "主键ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Integer id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {

                Enterprise Enterprise = iEnterpriseService.getDetailById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(Enterprise);
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

    @ApiOperation(value = "企业新增或者修改", notes = "企业新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody Enterprise enterprise) {

        ResultObject resultObject = new ResultObject();

        try {

            boolean b = iEnterpriseService.saveOrUpdate(enterprise);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 删除企业
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除企业", notes = "删除企业")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = ResultObject.failure();

        try {
            QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
            enterpriseQueryWrapper.lambda().eq(Enterprise::getParentId, id);
            List<Enterprise> enterpriseList = iEnterpriseService.list(enterpriseQueryWrapper);
            if (!enterpriseList.isEmpty()) {
                resultObject.setMsg("该公司还有子公司存在，暂不能删除！");
                return resultObject;
            }

            Boolean b = iEnterpriseService.removeById(id);
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }
}
