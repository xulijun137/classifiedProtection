package com.jwell.classifiedProtection.controller.app;


import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.AssetCategory;
import com.jwell.classifiedProtection.service.IAssetCategoryService;
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
 * 关联资产 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "资产类型接口集合", tags = "资产类型接口集合")
@RestController
@RequestMapping("/app/asset-category")
public class AssetCategoryAppController extends BaseController {

    @Autowired
    private IAssetCategoryService iAssetCategoryService;

    /**
     * 所有资产类型列表
     *
     * @return
     */
    @ApiOperation(value = "所有资产类型列表", notes = "所有资产类型列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationAdviceTaskList() {

        ResultObject resultObject = ResultObject.failure();
        try {
            List<AssetCategory> systemList = iAssetCategoryService.list();
            resultObject = ResultObject.success();
            resultObject.setData(systemList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultObject;
    }

}
