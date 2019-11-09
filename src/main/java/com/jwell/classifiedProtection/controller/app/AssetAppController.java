package com.jwell.classifiedProtection.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.enums.TaskProgressEnum;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.entry.Asset;
import com.jwell.classifiedProtection.service.IAssessTaskService;
import com.jwell.classifiedProtection.service.IAssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/app/asset")
public class AssetAppController extends BaseController {

    @Autowired
    private IAssetService iAssetService;

    @Autowired
    private IAssessTaskService iAssessTaskService;

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
            @ApiImplicitParam(paramType = "query", name = "categorgyId", value = "相关类型ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "assetType", value = "资产类别", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getAssetList(String assetType, Integer categorgyId,
                                     Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.failure();

        try {
            QueryWrapper<Asset> assetQueryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(assetType)) {
                assetQueryWrapper.lambda().eq(Asset::getAssetType, assetType);
            }
            if (categorgyId != null) {
                assetQueryWrapper.lambda().eq(Asset::getCategoryId, categorgyId);
            }
            IPage<Asset> AssetIPage = iAssetService.paging(pageNum, pageSize, assetQueryWrapper);
            resultObject = ResultObject.success();
            resultObject.setData(AssetIPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 资产详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "资产详情", notes = "资产详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "资产ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(Integer id) {

        ResultObject resultObject = ResultObject.failure();
        try {

            if (id != null) {

                Asset Asset = iAssetService.getDetailById(id);
                resultObject = ResultObject.success();
                resultObject.setData(Asset);
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

    @ApiOperation(value = "资产新增或者修改", notes = "资产新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody Asset asset) {

        ResultObject resultObject = ResultObject.failure();

        try {

            boolean b = iAssetService.saveOrUpdate(asset);
            resultObject = ResultObject.success();

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    @ApiOperation(value = "资产删除", notes = "资产删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject delete(Integer id) {

        ResultObject resultObject = ResultObject.failure();

        try {

            boolean b = iAssetService.removeById(id);
            resultObject = ResultObject.success();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }

    @ApiOperation(value = "关联资产下一步", notes = "关联资产下一步")
    @RequestMapping(value = "/next", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "taskId", value = "评测任务ID"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "assetId", value = "关联资产ID"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject next(Integer taskId, Integer assetId) throws Exception {

        ResultObject resultObject = ResultObject.failure();
        try {
            AssessTask assessTask = new AssessTask();
            assessTask.setId(taskId);
            assessTask.setAssetId(assetId);
            assessTask.setProgressState(TaskProgressEnum.ONLIE_ASSESS.getValue());
            boolean b = iAssessTaskService.saveOrUpdate(assessTask);
            resultObject = ResultObject.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;
    }
}
