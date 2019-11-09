package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.Log;
import com.jwell.classifiedProtection.service.ILogService;
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

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-08
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "日志接口集合", tags = "日志接口集合")
@RestController
@RequestMapping("/backend/log")
public class LogController extends BaseController {

    @Autowired
    private ILogService iLogService;

    /**
     * 整改工具分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "整改工具分页列表", notes = "获取整改工具分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "moduleName", value = "模块名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "操作人", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "operationType", value = "操作类型", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "时间", dataType = "LocalDateTime", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getLogList(String moduleName, Integer userId, String operationType,
                                   @RequestParam(value = "createTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createTime,
                                   Integer pageNum, Integer pageSize) {

        ResultObject resultObject = new ResultObject();

        try {
            QueryWrapper<Log> logQueryWrapper = new QueryWrapper<>();
            logQueryWrapper.lambda().orderByDesc(Log::getCreateTime);

            if (!StringUtils.isEmpty(moduleName)) {
                logQueryWrapper.lambda().like(Log::getModuleName, moduleName);
            }

            if (userId != null) {
                logQueryWrapper.lambda().eq(Log::getUserId, userId);
            }

            if (!StringUtils.isEmpty(operationType)) {
                logQueryWrapper.lambda().eq(Log::getOperationType, operationType);
            }

            if (createTime != null) {
                LocalDateTime localMin = LocalDateTime.of(createTime.toLocalDate(), LocalTime.MIN);
                LocalDateTime localMax = LocalDateTime.of(createTime.toLocalDate(), LocalTime.MAX);
                logQueryWrapper.lambda().between(Log::getCreateTime, localMin, localMax);
            }

            IPage<Log> LogIPage = iLogService.paging(pageNum, pageSize, logQueryWrapper);
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(LogIPage);
        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询整改工具详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询整改工具", notes = "查询整改工具详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "整改工具ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Integer id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {

                Log Log = iLogService.getDetailById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(Log);
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
}
