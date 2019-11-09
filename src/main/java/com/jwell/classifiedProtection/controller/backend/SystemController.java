package com.jwell.classifiedProtection.controller.backend;


import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.System;
import com.jwell.classifiedProtection.service.ISystemService;
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
@Transactional(rollbackFor = Exception.class)
@Api(value = "系统接口", tags = "系统接口")
@RestController
@RequestMapping("/backend/system")
public class SystemController extends BaseController {


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

}
