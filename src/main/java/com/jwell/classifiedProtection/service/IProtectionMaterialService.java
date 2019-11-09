package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.ProtectionMaterial;

import java.io.Serializable;

/**
 * <p>
 * 等保资料 服务类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-05
 */
public interface IProtectionMaterialService extends IService<ProtectionMaterial> {

    /**
     * 等保资料分页列表
     * @param queryWrapper
     * @return
     */
    IPage<ProtectionMaterial> paging(Integer pageNum, Integer pageSize,
                                     QueryWrapper<ProtectionMaterial> queryWrapper);

    /**
     * 等保资料详情
     * @param id
     * @return
     */
    ProtectionMaterial getDetailById(Serializable id);

}
