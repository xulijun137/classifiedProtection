package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.RectificationAdvice;
import com.jwell.classifiedProtection.entry.vo.RectificationAdviceVo;
import com.jwell.classifiedProtection.service.IRectificationAdviceService;
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
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 整改建议任务 前端控制器
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "整改建议任务接口", tags = "整改建议任务接口")
@RestController
@RequestMapping("/backend/rectification-advice")
public class RectificationAdviceController extends BaseController {

    @Autowired
    private IRectificationAdviceService iRectificationAdviceService;

    /**
     * 整改建议任务分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "整改建议任务分页列表", notes = "获取整改建议任务分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemId", value = "系统", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "creatorId", value = "创建人", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "enterpriseId", value = "单位", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "时间", dataType = "LocalDateTime", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "progressState", value = "进度状态", dataType = "String"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationAdviceVoList(
            Integer systemId, Integer creatorId, Integer enterpriseId, String progressState,
            @RequestParam(value = "createTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createTime,
            Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.success();

        try {
            IPage iPage = null;
            if (pageNum != null && pageSize != null) {
                iPage = new Page(pageNum, pageSize);
            } else {
                resultObject = ResultObject.failure();
                resultObject.setMsg("分页页长和页码不能为空1");
                return resultObject;
            }

            RectificationAdvice rectificationAdvice = new RectificationAdvice();
            rectificationAdvice.setSystemId(systemId);
            rectificationAdvice.setCreatorId(creatorId);
            rectificationAdvice.setEnterpriseId(enterpriseId);
            rectificationAdvice.setProgressState(progressState);
            rectificationAdvice.setCreateTime(createTime);

            IPage<RectificationAdviceVo> rectificationAdviceTaskIPage = iRectificationAdviceService.selectRectificationAdviceVoPage(iPage, rectificationAdvice);

            resultObject.setData(rectificationAdviceTaskIPage);
        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询整改建议任务详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询整改建议任务", notes = "查询整改建议任务详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "整改建议任务ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Serializable id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {

                RectificationAdvice RectificationAdvice = iRectificationAdviceService.getDetailById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(RectificationAdvice);
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

    @ApiOperation(value = "整改建议任务新增或者修改", notes = "整改建议任务新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody RectificationAdvice RectificationAdvice) {

        ResultObject resultObject = new ResultObject();

        try {

            boolean b = iRectificationAdviceService.saveOrUpdate(RectificationAdvice);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }
}
