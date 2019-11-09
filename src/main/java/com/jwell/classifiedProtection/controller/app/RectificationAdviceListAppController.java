package com.jwell.classifiedProtection.controller.app;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.excel.ExcelUtils;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.RectificationAdviceList;
import com.jwell.classifiedProtection.entry.excel.RectificationAdviceModel;
import com.jwell.classifiedProtection.entry.vo.RectificationAdviceListVo;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.jwell.classifiedProtection.service.IRectificationAdviceListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;

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
@RequestMapping("/app/rectification-advice-list")
public class RectificationAdviceListAppController extends BaseController {

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
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "等保类型", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationAdviceListList(
            Integer categoryId,
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
            rectificationAdviceList.setCategoryId(categoryId);

            IPage<RectificationAdviceListVo> rectificationAdviceTaskIPage = iRectificationAdviceListService.selectRectificationAdviceListVoPage(iPage, rectificationAdviceList);
            resultObject = ResultObject.success();
            resultObject.setData(rectificationAdviceTaskIPage);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    @ApiOperation(value = "导出整改建议Excel表格", notes = "导出整改建议Excel表格")
    @RequestMapping(value = "/exprotExcel", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResponseEntity exprotExcel(@RequestBody List<RectificationAdviceModel> dataList) {

        try {
            //数据写入到字节流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            boolean flag = ExcelUtils.writeExcel(bos, RectificationAdviceModel.class, dataList);

            //下载文件
            String fileName = "下载整改建议.xls";
            log.info("开始下载导出的Excel文件");
            return ExcelUtils.downloadExcel(fileName, bos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
