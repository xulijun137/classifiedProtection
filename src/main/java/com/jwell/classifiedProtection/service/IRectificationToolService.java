package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.RectificationTool;

import java.io.Serializable;

/**
 * <p>
 * 系统 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-05
 */
public interface IRectificationToolService extends IService<RectificationTool> {

    /**
     * 整改工具分页列表
     * @param queryWrapper
     * @return
     */
    IPage<RectificationTool> paging(Integer pageNum, Integer pageSize,
                                    QueryWrapper<RectificationTool> queryWrapper);

    /**
     * 整改工具详情
     * @param id
     * @return
     */
    RectificationTool getDetailById(Serializable id);

}
