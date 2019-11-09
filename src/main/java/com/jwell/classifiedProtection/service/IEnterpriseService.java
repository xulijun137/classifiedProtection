package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.Enterprise;
import com.jwell.classifiedProtection.entry.vo.EnterpriseVo;

import java.io.Serializable;

/**
 * <p>
 * 企业 服务类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
public interface IEnterpriseService extends IService<Enterprise> {

    /**
     * 企业分页列表
     * @param iPage
     * @param enterprise
     * @return
     */
    IPage<EnterpriseVo> selectEnterpriseVoPaging(IPage<EnterpriseVo> iPage, Enterprise enterprise);

    /**
     * 企业详情
     * @param id
     * @return
     */
    Enterprise getDetailById(Serializable id);
    
}
