package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.utils.FileUtils;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.RectificationTool;
import com.jwell.classifiedProtection.service.IRectificationToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.LocalDateTime;

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
@RequestMapping("/backend/rectification-tool")
public class RectificationToolController extends BaseController {

    @Autowired
    private IRectificationToolService iRectificationToolService;

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
            @ApiImplicitParam(paramType = "query", name = "toolName", value = "名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "fileName", value = "附件名称", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "时间", dataType = "LocalDateTime", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationToolList(String toolName, String fileName, String type,
                                                 @RequestParam(value = "createTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createTime,
                                                 Integer pageNum, Integer pageSize) {

        ResultObject resultObject = new ResultObject();

        try {
            QueryWrapper<RectificationTool> rectificationToolQueryWrapper = new QueryWrapper<>();

            if (!StringUtils.isEmpty(toolName)) {
                rectificationToolQueryWrapper.lambda().eq(RectificationTool::getToolName, toolName);
            }

            if (!StringUtils.isEmpty(fileName)) {
                rectificationToolQueryWrapper.lambda().eq(RectificationTool::getFileName, fileName);
            }

            if (!StringUtils.isEmpty(type)) {
                rectificationToolQueryWrapper.lambda().eq(RectificationTool::getType, type);
            }

            if (createTime != null) {
                rectificationToolQueryWrapper.lambda().eq(RectificationTool::getCreateTime, createTime);
            }

            IPage<RectificationTool> RectificationToolIPage = iRectificationToolService.paging(pageNum, pageSize,
                    rectificationToolQueryWrapper);
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(RectificationToolIPage);

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

                RectificationTool RectificationTool = iRectificationToolService.getDetailById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(RectificationTool);
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

    @ApiOperation(value = "整改工具新增或者修改", notes = "整改工具新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody RectificationTool rectificationTool) {

        ResultObject resultObject = new ResultObject();

        try {

            String filePath = "C:\\Users\\Ronnie\\Pictures\\first.py";

            byte[] fileBytes = FileUtils.fileToBytes(filePath);
            rectificationTool.setFileBlob(fileBytes);
            String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);

            File tempFile = new File(filePath.trim());
            rectificationTool.setFileName(tempFile.getName());

            rectificationTool.setDescription("这是一个测试整改工具的描述！");
            rectificationTool.setToolName("整改工具1");
            rectificationTool.setType("杀毒类");

            boolean b = iRectificationToolService.saveOrUpdate(rectificationTool);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    @ApiOperation(value = "下载整改工具", notes = "下载整改工具")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "整改工具id", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResponseEntity toolDownload(Integer id) throws Exception{

        try {
            RectificationTool rectificationTool = iRectificationToolService.getDetailById(id);
            String fileName = rectificationTool.getToolName();

            HttpHeaders headers = new HttpHeaders();// 设置一个head
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);// 内容是字节流
            headers.setPragma("no-cache");
            headers.setExpires(0);
            // 开始下载
            return new ResponseEntity<byte[]>(rectificationTool.getFileBlob(), headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @ApiOperation(value = "上传整改工具", notes = "上传整改工具")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "toolName", value = "工具名称", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "author", value = "作者", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "type", value = "工具类型", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "description", value = "工具描述", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public void toolUpload(MultipartFile file, String author,
                           String toolName, String type, String description) {

        try {
            byte[] fileBytes = file.getBytes();

            String fileName = file.getOriginalFilename();

            //这里提供文件服务器地址
            String filePath = "C:\\" + this.getClass().getSimpleName() + "\\";
            File myPath = new File(filePath);
            if (!myPath.exists() && !myPath.isDirectory()) {
                //若此目录不存在，则创建之
                myPath.mkdirs();
                System.out.println("创建文件夹路径为：" + filePath);
            }
            File dest = new File(filePath + fileName);

            file.transferTo(dest);
            RectificationTool rectificationTool = new RectificationTool(
                    toolName, fileName,
                    type, author, null,
                    fileBytes, description);
            boolean b = iRectificationToolService.save(rectificationTool);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
