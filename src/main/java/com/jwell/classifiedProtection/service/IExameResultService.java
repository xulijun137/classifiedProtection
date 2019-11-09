package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.ExameResult;

import java.io.Serializable;

/**
 * <p>
 * 评测结果 服务类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
public interface IExameResultService extends IService<ExameResult> {

    /**
     * 评测结果分页列表
     * @param queryWrapper
     * @return
     */
    IPage<ExameResult> paging(Integer pageNum, Integer pageSize,
                                 QueryWrapper<ExameResult> queryWrapper);

    /**
     * 评测结果详情
     * @param id
     * @return
     */
    ExameResult getDetailById(Serializable id);
    
}
