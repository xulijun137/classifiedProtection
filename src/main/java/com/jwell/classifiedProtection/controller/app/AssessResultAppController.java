package com.jwell.classifiedProtection.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.enums.AnswerResultEnum;
import com.jwell.classifiedProtection.commons.enums.TaskProgressEnum;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.AssessResult;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.service.IAssessResultService;
import com.jwell.classifiedProtection.service.IAssessTaskService;
import com.jwell.classifiedProtection.service.IFileBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评测结果 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-31
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "测评结果接口", tags = "测评结果接口")
@RestController
@RequestMapping("/app/assess-result")
public class AssessResultAppController extends BaseController {

    @Autowired
    private IAssessResultService iAssessResultService;

    @Autowired
    private IFileBankService iFileBankService;

    @Autowired
    private IAssessTaskService iAssessTaskService;

    /**
     * 评测结果的保存
     *
     * @param assessResultDtoList
     * @return
     */
    @ApiOperation(value = "评测结果的保存", notes = "评测结果的保存")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody List<AssessResult> assessResultDtoList) {

        ResultObject resultObject = ResultObject.failure();

        try {
            for (AssessResult assessResult : assessResultDtoList) {

                boolean b = iAssessResultService.saveOrUpdate(assessResult);

//
            }

            resultObject = ResultObject.success();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 评测结果报表
     *
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "评测结果报表", notes = "评测结果报表")
    @RequestMapping(value = "/piechart", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "categoryId", value = "类别Id", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject piechart(Integer categoryId) {

        ResultObject resultObject = ResultObject.failure();

        try {
            Map dataMap = new HashMap<>();
            QueryWrapper<AssessResult> assessResultQueryWrapper1 = new QueryWrapper<>();
            if (categoryId != null && categoryId != 0) {
                assessResultQueryWrapper1.lambda().eq(AssessResult::getCategoryId, categoryId);
            }
            //全部的
            Integer totalAll = iAssessResultService.count(assessResultQueryWrapper1);
            //dataMap.put("totalAll", totalAll);

            List<Map> dataMapList = new ArrayList<>();

            //未测评的
            assessResultQueryWrapper1.lambda().isNull(AssessResult::getAnswer);
            Integer notAssessTotal = iAssessResultService.count(assessResultQueryWrapper1);
//            BigDecimal notAssessBigDecimal = BigDecimal.valueOf(notAssessTotal)
//                    .divide(BigDecimal.valueOf(totalAll),2, RoundingMode.HALF_UP);
            Map data1 = new HashMap();
            data1.put("name", "未测评");
            data1.put("value", notAssessTotal);
            dataMapList.add(data1);

            //符合的
            Integer coincidenceTotal = iAssessResultService.countAssessResultList(categoryId, AnswerResultEnum.COINCIDENCE.getValue());
//            BigDecimal coincidenceBigDecimal = BigDecimal.valueOf(coincidenceTotal)
//                    .divide(BigDecimal.valueOf(totalAll),2, RoundingMode.HALF_UP);
            Map data2 = new HashMap();
            data2.put("name", "符合");
            data2.put("value", coincidenceTotal);
            dataMapList.add(data2);

            //部分符合的
            Integer partialCoincidenceTotal = iAssessResultService.countAssessResultList(categoryId, AnswerResultEnum.PARTIAL_COINCIDENCE.getValue());
            Map data3 = new HashMap();
            data3.put("name", "部分符合");
            data3.put("value", partialCoincidenceTotal);
            dataMapList.add(data3);

            //不符合的
            Integer notCoincidenceTotal = iAssessResultService.countAssessResultList(categoryId, AnswerResultEnum.NOT_COINCIDENCE.getValue());
            Map data4 = new HashMap();
            data4.put("name", "不符合");
            data4.put("value", notCoincidenceTotal);
            dataMapList.add(data4);

            //不适用的
            Integer notApplicable = iAssessResultService.countAssessResultList(categoryId, AnswerResultEnum.NOT_APPLICABLE.getValue());
            Map data5 = new HashMap();
            data5.put("name", "不适用");
            data5.put("value", notApplicable);
            dataMapList.add(data5);

            dataMap.put("list", dataMapList);

            //计算等级（优良合格差）
            BigDecimal part = BigDecimal.valueOf(partialCoincidenceTotal).multiply(new BigDecimal(0.5));
            BigDecimal total = BigDecimal.valueOf(coincidenceTotal).add(part);
            Integer totalLeft = totalAll - notApplicable;
            BigDecimal proportion = total.divide(BigDecimal.valueOf(totalLeft), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            if ((proportion.compareTo(BigDecimal.valueOf(90)) == 1 || proportion.compareTo(BigDecimal.valueOf(90)) == 0) &&
                    (proportion.compareTo(BigDecimal.valueOf(100)) == -1) || proportion.compareTo(BigDecimal.valueOf(100)) == 0) {
                dataMap.put("level", "优秀");
            } else if ((proportion.compareTo(BigDecimal.valueOf(80)) == 1 || proportion.compareTo(BigDecimal.valueOf(80)) == 0) &&
                    proportion.compareTo(BigDecimal.valueOf(90)) == -1) {
                dataMap.put("level", "良好");
            } else if ((proportion.compareTo(BigDecimal.valueOf(60)) == 1 || proportion.compareTo(BigDecimal.valueOf(60)) == 0) &&
                    proportion.compareTo(BigDecimal.valueOf(80)) == -1) {
                dataMap.put("level", "合格");
            } else {
                dataMap.put("level", "差");
            }
            resultObject = ResultObject.success();
            resultObject.setData(dataMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;

    }


    @ApiOperation(value = "评测结果下一步", notes = "评测结果下一步")
    @RequestMapping(value = "/next", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "taskId", value = "评测任务ID"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject next(Integer taskId) throws Exception {

        ResultObject resultObject = ResultObject.failure();
        try {
            AssessTask assessTask = new AssessTask();
            assessTask.setId(taskId);
            assessTask.setProgressState(TaskProgressEnum.RECTIFICATION_ADVICE.getValue());
            boolean b = iAssessTaskService.saveOrUpdate(assessTask);
            resultObject = ResultObject.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;
    }
}
