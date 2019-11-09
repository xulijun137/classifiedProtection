package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.QuestionBank;

import java.io.Serializable;

/**
 * <p>
 * 测评问题库 服务类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
public interface IQuestionBankService extends IService<QuestionBank> {


    /**
     * 测评问题库分页列表
     * @param queryWrapper
     * @return
     */
    IPage<QuestionBank> paging(Integer pageNum, Integer pageSize,
                               QueryWrapper<QuestionBank> queryWrapper);

    /**
     * 测评问题库详情
     * @param id
     * @return
     */
    QuestionBank getDetailById(Serializable id);
    
}
