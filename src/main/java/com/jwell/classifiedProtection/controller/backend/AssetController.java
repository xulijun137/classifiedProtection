package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.Asset;
import com.jwell.classifiedProtection.service.IAssetService;
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
import java.util.List;

/**
 * <p>
 * 关联资产 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-08
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "资产接口集合", tags = "资产接口集合")
@RestController
@RequestMapping("/backend/asset")
public class AssetController extends BaseController {

    @Autowired
    private IAssetService iAssetService;

    /**
     * 所有资产的列表
     *
     * @return
     */
    @ApiOperation(value = "所有资产的列表", notes = "所有资产列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationAdviceTaskList() {

        ResultObject resultObject = ResultObject.failure();
        try {
            List<Asset> systemList = iAssetService.list();
            resultObject = ResultObject.success();
            resultObject.setData(systemList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultObject;
    }

    /**
     * 资产分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "资产分页列表", notes = "获取资产分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键字", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getAssetList(String keyword, Integer pageNum, Integer pageSize) {

        ResultObject resultObject = new ResultObject();

        try {
            QueryWrapper<Asset> AssetQueryWrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty(keyword)){
                AssetQueryWrapper.lambda().eq(Asset::getAssetName, keyword);
            }
            IPage<Asset> AssetIPage = iAssetService.paging(pageNum,pageSize,AssetQueryWrapper);
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(AssetIPage);
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
    @ApiOperation(value = "企业", notes = "企业详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "资产ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Integer id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {

                Asset Asset = iAssetService.getDetailById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(Asset);
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

    @ApiOperation(value = "资产新增或者修改", notes = "资产新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(Asset asset) {

        ResultObject resultObject = new ResultObject();

        try {
            boolean b = iAssetService.saveOrUpdate(asset);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }
}
