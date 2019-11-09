package com.jwell.classifiedProtection.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.entry.RectificationTool;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.jwell.classifiedProtection.service.IRectificationToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-05
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "整改工具接口", tags = "整改工具接口")
@RestController
@RequestMapping("/app/rectification-tool")
public class RectificationToolAppController extends BaseController {

    @Autowired
    private IRectificationToolService iRectificationToolService;

    @Autowired
    private IFileBankService iFileBankService;

    /**
     * 整改工具分页列表
     *
     * @return
     */
    @ApiOperation(value = "整改工具分页列表", notes = "获取整改工具分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationToolList() {

        ResultObject resultObject = new ResultObject();

        try {
            List<RectificationTool> rectificationToolList = iRectificationToolService.list();
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(rectificationToolList);

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

        ResultObject resultObject = ResultObject.failure();
        try {

            if (id != null) {
                Map dataMap = new HashMap<>();
                RectificationTool rectificationTool = iRectificationToolService.getDetailById(id);
                dataMap.put("rectificationTool", rectificationTool);

                QueryWrapper<FileBank> fileBankQueryWrapper = new QueryWrapper<>();
                fileBankQueryWrapper.lambda().eq(FileBank::getTableName, "biz_rectification_tool")
                        .eq(FileBank::getForeignId, rectificationTool.getId());
                FileBank fileBankList = iFileBankService.getOne(fileBankQueryWrapper);
                dataMap.put("fileBankList", fileBankList);

                resultObject = ResultObject.success();
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

    @ApiOperation(value = "预览或者下载整改工具", notes = "预览或者下载整改工具")
    @RequestMapping(value = "/viewOrDownload", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "文件ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "type", value = "1=预览，2=下载", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResponseEntity toolDownload(Integer id, Integer type) throws Exception {

        try {

            FileBank fileBank = iFileBankService.getById(id);

            HttpHeaders headers = new HttpHeaders();// 设置一个head

            if (type == 1) {
                headers.setContentType(MediaType.APPLICATION_PDF);// 内容是字节流
            } else if (type == 2) {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);// 内容是字节流
                headers.setContentDispositionFormData("attachment",
                        new String(fileBank.getFileName().getBytes("GBK"), "ISO8859-1"));
            }

            headers.setPragma("no-cache");
            headers.setExpires(0);
            // 开始下载
            return new ResponseEntity<byte[]>(fileBank.getFileBlob(), headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
}
