package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.Asset;
import com.jwell.classifiedProtection.mapper.AssetMapper;
import com.jwell.classifiedProtection.service.IAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 关联资产 服务实现类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-08
 */
@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements IAssetService {

    @Autowired
    private AssetMapper assetMapper;

    @Override
    public IPage<Asset> paging(Integer pageNum, Integer pageSize,
                                    QueryWrapper<Asset> queryWrapper) {

        queryWrapper.lambda().orderByDesc(Asset::getCreateTime);

        IPage iPage = new Page(pageNum, pageSize);
        IPage<Asset> userIPage = assetMapper.selectPage(iPage, queryWrapper);
        return userIPage;

    }

    @Override
    public Asset getDetailById(Serializable id) {

        Asset asset = assetMapper.selectById(id);
        return asset;
    }
}
