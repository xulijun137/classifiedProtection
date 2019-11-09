package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.entry.vo.AssessTaskVo;

/**
 * <p>
 * 评测结果 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
public interface IAssessTaskService extends IService<AssessTask> {

    /**
     * 评测任务分页列表
     *
     * @param iPage
     * @param assessTask
     * @return
     */
    IPage<AssessTaskVo> selectAssessTaskVoPaging(IPage<AssessTaskVo> iPage, AssessTask assessTask);

}
