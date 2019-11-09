package com.jwell.classifiedProtection.controller.app;


import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.enums.TaskProgressEnum;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.entry.System;
import com.jwell.classifiedProtection.entry.dto.SystemDto;
import com.jwell.classifiedProtection.service.IAssessTaskService;
import com.jwell.classifiedProtection.service.ISystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 系统登记 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-10
 */
@Slf4j
//@Transactional(rollbackFor = Exception.class)
@Api(value = "系统接口", tags = "系统接口")
@RestController
@RequestMapping("/app/system")
public class SystemAppController extends BaseController {

    @Autowired
    private IAssessTaskService iAssessTaskService;

    @Autowired
    private ISystemService iSystemService;

    /**
     * 返回所有系统的列表
     *
     * @return
     */
    @ApiOperation(value = "系统列表", notes = "系统列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationAdviceTaskList() {

        ResultObject resultObject = ResultObject.success();
        try {
            List<System> systemList = iSystemService.list();
            resultObject.setData(systemList);
        } catch (Exception e) {
            resultObject = ResultObject.failure();
        }

        return resultObject;
    }

    @ApiOperation(value = "系统新增或者修改", notes = "系统新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody SystemDto systemDto) {

        ResultObject resultObject = ResultObject.failure();

        try {
            System systemObject = systemDto.getSystem();
            Integer taskId = systemDto.getTaskId();

            boolean b = iSystemService.saveOrUpdate(systemObject);
            if (b) {
                AssessTask assessTask = new AssessTask();
                assessTask.setId(taskId);
                assessTask.setProgressState(TaskProgressEnum.SYSTEM_GRADING.getValue());
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

    @ApiOperation(value = "系统（登记）详情", notes = "查看系统（登记）详情")
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

                System system = iSystemService.getById(id);
                resultObject = ResultObject.success();
                resultObject.setData(system);

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

}
