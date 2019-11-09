package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.Asset;

import java.io.Serializable;

/**
 * <p>
 * 关联资产 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-08
 */
public interface IAssetService extends IService<Asset> {

    /**
     * 资产分页列表
     * @param queryWrapper
     * @return
     */
    IPage<Asset> paging(Integer pageNum, Integer pageSize,
                             QueryWrapper<Asset> queryWrapper);

    /**
     * 资产详情
     * @param id
     * @return
     */
    Asset getDetailById(Serializable id);
}
