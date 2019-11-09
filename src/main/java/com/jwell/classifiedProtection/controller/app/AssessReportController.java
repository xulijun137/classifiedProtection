package com.jwell.classifiedProtection.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.AssessReport;
import com.jwell.classifiedProtection.service.IAssessReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * <p>
 * 评测结果 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "评审报告接口集合", tags = "评审报告接口集合")
@RestController
@RequestMapping("/app/assess-report")
public class AssessReportController extends BaseController {

    @Autowired
    private IAssessReportService iAssessReportService;

    /**
     * 机构组织库分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "机构组织库分页列表", notes = "获取机构组织库分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键字", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getAssessReportList(String keyword, Integer pageNum, Integer pageSize) {

        ResultObject resultObject = new ResultObject();

        try {
            QueryWrapper<AssessReport> AssessReportQueryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(keyword)) {
                AssessReportQueryWrapper.lambda().eq(AssessReport::getAssetId, keyword);
            }

            IPage<AssessReport> iPage = new Page<>(pageNum, pageSize);
            IPage<AssessReport> AssessReportIPage = iAssessReportService.page(iPage, AssessReportQueryWrapper);
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(AssessReportIPage);
        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询机构组织库详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询机构组织库", notes = "查询机构组织库详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "机构组织库ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Serializable id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {

                AssessReport AssessReport = iAssessReportService.getById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(AssessReport);
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

    @ApiOperation(value = "组织机构新增或者修改", notes = "组织机构新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody AssessReport AssessReport) {

        ResultObject resultObject = new ResultObject();

        try {

            boolean b = iAssessReportService.saveOrUpdate(AssessReport);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 机构组织删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "机构组织删除", notes = "删除机构组织")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = new ResultObject();

        try {
            Boolean aBoolean = iAssessReportService.removeById(id);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }
    
}
