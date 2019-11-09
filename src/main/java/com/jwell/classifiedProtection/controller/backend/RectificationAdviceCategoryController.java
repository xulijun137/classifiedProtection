package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.RectificationAdviceCategory;
import com.jwell.classifiedProtection.service.IRectificationAdviceCategoryService;
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
 * 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-17
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "整改建议类别接口", tags = "整改建议类别接口")
@RestController
@RequestMapping("/backend/rectification-advice-category")
public class RectificationAdviceCategoryController extends BaseController {

    @Autowired
    private IRectificationAdviceCategoryService iRectificationAdviceCategoryService;

    /**
     * 所有类型列表
     *
     * @return
     */
    @ApiOperation(value = "所有类型列表", notes = "所有类型列表")
    @RequestMapping(value = "/mainCategory", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "version", value = "1.0或者2.0", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject mainCategory(String version) {

        ResultObject resultObject = ResultObject.failure();
        try {
            QueryWrapper<RectificationAdviceCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(RectificationAdviceCategory::getParentId, 0)
                    .eq(RectificationAdviceCategory::getVersion, version);
            List<RectificationAdviceCategory> list = iRectificationAdviceCategoryService.list(queryWrapper);
            resultObject = ResultObject.success();
            resultObject.setData(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultObject;
    }

    /**
     * 根据父级Id获取子类型列表详细信息
     *
     * @return
     */
    @ApiOperation(value = "根据父级Id获取子类型列表", notes = "根据父级Id获取子类型列表")
    @RequestMapping(value = "/getChildByParentId", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "id", value = "类型ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getCategoryDetail(Integer id) {

        ResultObject resultObject = ResultObject.failure();
        try {
            QueryWrapper<RectificationAdviceCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(RectificationAdviceCategory::getParentId, id);
            List<RectificationAdviceCategory> list = iRectificationAdviceCategoryService.list(queryWrapper);
            resultObject = ResultObject.success();
            resultObject.setData(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultObject;
    }
}
