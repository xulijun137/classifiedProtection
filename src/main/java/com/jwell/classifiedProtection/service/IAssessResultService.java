package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.AssessResult;

/**
 * <p>
 * 评测结果 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
public interface IAssessResultService extends IService<AssessResult> {

    /**
     * 根据成绩查询每种成绩的题目数目
     *
     * @param categoryId
     * @param answer
     * @return
     */
    Integer countAssessResultList(Integer categoryId, String answer);
}
