package com.jwell.classifiedProtection.controller.backend;


import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.service.IFileBankService;
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

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 存储附件的数据表 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-15
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "附件接口集合", tags = "附件接口集合")
@RestController
@RequestMapping("/backend/file-bank")
public class FileBankController extends BaseController {

    @Autowired
    private IFileBankService iFileBankService;

    /**
     * 删除附件/文件
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除附件/文件", notes = "删除附件/文件")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = ResultObject.failure();

        try {
            Boolean b = iFileBankService.removeById(id);
            resultObject = ResultObject.success();
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }
}
