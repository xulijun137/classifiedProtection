package com.jwell.classifiedProtection.controller.app;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.enums.TaskProgressEnum;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.entry.vo.AssessTaskVo;
import com.jwell.classifiedProtection.service.IAssessTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
@Api(value = "评审任务接口集合", tags = "评审任务接口集合")
@RestController
@RequestMapping("/app/assess-task")
public class AssessTaskController extends BaseController<AssessTask> {

    @Autowired
    private IAssessTaskService iAssessTaskService;

    /**
     * 评测任务分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "评测任务分页列表", notes = "评测任务分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "taskName", value = "任务名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject paging(String taskName, Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.failure();

        try {

            AssessTask assessTask = new AssessTask();
            assessTask.setTaskName(taskName);

            IPage<AssessTaskVo> iPage = null;
            if (pageNum == null || pageSize == null) {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("页长或页码不能为空");
                return resultObject;
            } else {
                iPage = new Page(pageNum, pageSize);
            }
            IPage<AssessTaskVo> AssessTaskVoIPage = iAssessTaskService.selectAssessTaskVoPaging(iPage, assessTask);
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(AssessTaskVoIPage);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    @ApiOperation(value = "评测任务新增或者修改", notes = "评测任务新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(AssessTask assessTask) {

        ResultObject resultObject = ResultObject.failure();

        try {
            if (assessTask.getOrganizationId() != null) {
                assessTask.setProgressState(TaskProgressEnum.SYSTEM_REGISTER.getValue());
            }
            boolean b = iAssessTaskService.saveOrUpdate(assessTask);
            if (b) {
                resultObject = ResultObject.success();
                resultObject.setData(assessTask);
            }

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
            Boolean aBoolean = iAssessTaskService.removeById(id);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }
    
}
