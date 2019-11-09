package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.QuestionBank;
import com.jwell.classifiedProtection.service.IQuestionBankService;
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
import java.io.Serializable;

/**
 * <p>
 * 测评问题库 前端控制器
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "测评问题库接口", tags = "测评问题库接口")
@RestController
@RequestMapping("/backend/question-bank")
public class QuestionBankController extends BaseController {

    @Autowired
    private IQuestionBankService iQuestionBankService;

    /**
     * 评测问题库分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "评测问题库分页列表", notes = "获取评测问题库分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键字", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getQuestionBankList(String keyword,Integer pageNum, Integer pageSize) {

        ResultObject resultObject = new ResultObject();

        try {
            QueryWrapper<QuestionBank> questionBankQueryWrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty(keyword)){
                questionBankQueryWrapper.lambda().eq(QuestionBank::getQuestionName,keyword);
            }
            IPage<QuestionBank> QuestionBankIPage = iQuestionBankService.paging(pageNum,pageSize,questionBankQueryWrapper);
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(QuestionBankIPage);
        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询评测问题库详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询评测问题库", notes = "查询评测问题库详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "评测问题库ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Serializable id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {

                QuestionBank QuestionBank = iQuestionBankService.getDetailById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(QuestionBank);
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

    @ApiOperation(value = "评测题库新增或者修改", notes = "评测题库新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody QuestionBank questionBank1) {

        ResultObject resultObject = new ResultObject();

        try {

            boolean b = iQuestionBankService.saveOrUpdate(questionBank1);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }
}
