package com.jwell.classifiedProtection.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.components.redis.RedisUtil;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.ProtectionMaterial;
import com.jwell.classifiedProtection.entry.RectificationTool;
import com.jwell.classifiedProtection.entry.SecurityTemplate;
import com.jwell.classifiedProtection.service.IProtectionMaterialService;
import com.jwell.classifiedProtection.service.IRectificationToolService;
import com.jwell.classifiedProtection.service.ISecurityTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 首页 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-08
 */
@Slf4j
@Api(value = "首页接口集合", tags = "首页接口集合")
@RestController
@RequestMapping("/app/index")
public class IndexAppController extends BaseController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IProtectionMaterialService iProtectionMaterialService;

    @Autowired
    private IRectificationToolService iRectificationToolService;

    @Autowired
    private ISecurityTemplateService iSecurityTemplateService;
    
    /**
     * 首页请求
     *
     * @return
     */
    @ApiOperation(value = "首页接口", notes = "首页接口")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
        }
    )
    //@Cacheable(value="indexTmp")
    public ResultObject index() {

        ResultObject resultObject = new ResultObject();
        Map<String,Object> mapdata = new LinkedHashMap<>();

        try {
            /**
             * 等保资料
             */
            QueryWrapper<ProtectionMaterial> protectionInfoQueryWrapper = new QueryWrapper<>();
            protectionInfoQueryWrapper.lambda().orderByDesc(ProtectionMaterial::getCreateTime);
            IPage<ProtectionMaterial> protectionInfoIPage = new Page<>(1, 9);
            IPage<ProtectionMaterial> protectionInfoPage = iProtectionMaterialService.page(protectionInfoIPage, protectionInfoQueryWrapper);
            mapdata.put("protectionInfo",protectionInfoPage.getRecords()!=null?
                    protectionInfoPage.getRecords():null);

            /**
             * 安全模板
             */
            QueryWrapper<SecurityTemplate> securityTemplateQueryWrapper = new QueryWrapper<>();
            securityTemplateQueryWrapper.lambda().orderByDesc(SecurityTemplate::getCreateTime);
            IPage<SecurityTemplate> securityTemplateIPage = new Page<>(1,8);
            IPage<SecurityTemplate> securityTemplatePage = iSecurityTemplateService.page(securityTemplateIPage,securityTemplateQueryWrapper);
            mapdata.put("securityTemplate",securityTemplatePage.getRecords()!=null?
                    securityTemplatePage.getRecords():null);

            /**
             * 整改工具
             */
            QueryWrapper<RectificationTool> rectificationToolQueryWrapper = new QueryWrapper<>();
            rectificationToolQueryWrapper.lambda().orderByDesc(RectificationTool::getCreateTime);
            IPage<RectificationTool> rectificationToolIPage = new Page<>(1,8);
            IPage<RectificationTool> rectificationToolPage = iRectificationToolService.page(rectificationToolIPage,rectificationToolQueryWrapper);
            mapdata.put("rectificationTool",rectificationToolPage.getRecords()!=null?
                    rectificationToolPage.getRecords():null);

            resultObject.setData(mapdata);
        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

}
