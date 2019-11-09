package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.Log;

import java.io.Serializable;

/**
 * <p>
 * 系统等级 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-08
 */
public interface ILogService extends IService<Log> {

    /**
     * 日志分页列表
     * @param queryWrapper
     * @return
     */
    IPage<Log> paging(Integer pageNum, Integer pageSize,
                                    QueryWrapper<Log> queryWrapper);

    /**
     * 日志详情
     * @param id
     * @return
     */
    Log getDetailById(Serializable id);
}
