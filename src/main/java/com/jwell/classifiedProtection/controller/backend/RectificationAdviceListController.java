package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.entry.RectificationAdviceList;
import com.jwell.classifiedProtection.entry.vo.RectificationAdviceListVo;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.jwell.classifiedProtection.service.IRectificationAdviceListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 整改建议详情列表详情列表 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "整改建议详情接口", tags = "整改建议详情接口")
@RestController
@RequestMapping("/backend/rectification-advice-list")
public class RectificationAdviceListController extends BaseController {

    @Autowired
    private IRectificationAdviceListService iRectificationAdviceListService;

    @Autowired
    private IFileBankService iFileBankService;

    /**
     * 整改建议详情列表分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "整改建议详情列表分页列表", notes = "获取整改建议详情列表分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "adviceId", value = "整改建议详情ID", dataType = "Integer", required = false),
            @ApiImplicitParam(paramType = "query", name = "category", value = "等保类型", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationAdviceListList(
            Integer adviceId, String category,
            Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.failure();

        try {
            IPage iPage = null;
            if (pageNum != null && pageSize != null) {
                iPage = new Page(pageNum, pageSize);
            } else {
                resultObject.setMsg("分页页长和页码不能为空1");
                return resultObject;
            }

            RectificationAdviceList rectificationAdviceList = new RectificationAdviceList();
            rectificationAdviceList.setAdviceId(adviceId);
            rectificationAdviceList.setCategory(category);

            IPage<RectificationAdviceListVo> rectificationAdviceTaskIPage = iRectificationAdviceListService.selectRectificationAdviceListVoPage(iPage, rectificationAdviceList);
            resultObject = ResultObject.success();
            resultObject.setData(rectificationAdviceTaskIPage);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询整改建议详情列表详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询整改建议详情列表", notes = "查询整改建议详情列表详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "整改建议详情列表ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Serializable id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {
                Map dataMap = new HashMap<>();
                RectificationAdviceList rectificationAdviceList = iRectificationAdviceListService.getDetailById(id);
                dataMap.put("rectificationAdviceList", rectificationAdviceList);

                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_rectification_advice_list")
                        .eq(FileBank::getForeignId, rectificationAdviceList.getId());
                List<FileBank> fileBankList = iFileBankService.list(fileBankQueryWrapper);
                dataMap.put("fileBankList", fileBankList);

                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(dataMap);
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

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "整改建议详情列表新增或者修改", notes = "整改建议详情列表新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(RectificationAdviceList rectificationAdviceList,
                                     MultipartFile[] multipartFiles) {

        ResultObject resultObject = new ResultObject();

        try {

            boolean flag = iRectificationAdviceListService.saveOrUpdate(rectificationAdviceList);

            //处理上传的文件
            if (multipartFiles.length != 0) {
                for (MultipartFile file : multipartFiles) {
                    FileBank fileBank = new FileBank();
                    fileBank.setForeignId(rectificationAdviceList.getId());
                    fileBank.setFileBlob(file.getBytes());
                    fileBank.setFileName(file.getOriginalFilename());
                    fileBank.setTableName("biz_rectification_advice_list");
                    fileBank.setModuleName("整改建议管理");
                    fileBank.setFileType("整改建议附件");
                    boolean b = iFileBankService.saveOrUpdate(fileBank);
                }
            }

            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

}
