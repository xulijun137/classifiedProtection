package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.SecurityTemplate;

import java.io.Serializable;

/**
 * <p>
 * 安全制度模板 服务类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-07
 */
public interface ISecurityTemplateService extends IService<SecurityTemplate> {

    /**
     * 安全制度模板分页列表
     * @param queryWrapper
     * @return
     */
    IPage<SecurityTemplate> paging(Integer pageNum, Integer pageSize,
                                    QueryWrapper<SecurityTemplate> queryWrapper);

    /**
     * 安全制度模板详情
     * @param id
     * @return
     */
    SecurityTemplate getDetailById(Serializable id);
    
}
