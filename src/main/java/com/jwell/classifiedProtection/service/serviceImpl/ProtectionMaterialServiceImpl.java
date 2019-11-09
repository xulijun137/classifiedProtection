package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.mapper.ProtectionMaterialMapper;
import com.jwell.classifiedProtection.service.IProtectionMaterialService;
import com.jwell.classifiedProtection.entry.ProtectionMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 等保资料 服务实现类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-05
 */
@Service
public class ProtectionMaterialServiceImpl extends ServiceImpl<ProtectionMaterialMapper, ProtectionMaterial> implements IProtectionMaterialService {

    @Autowired
    private ProtectionMaterialMapper ProtectionMaterialMapper;

    @Override
    public IPage<ProtectionMaterial> paging(Integer pageNum, Integer pageSize,
                                            QueryWrapper<ProtectionMaterial> queryWrapper) {

        queryWrapper.lambda().orderByDesc(ProtectionMaterial::getCreateTime);

        IPage iPage = new Page(pageNum, pageSize);
        IPage<ProtectionMaterial> userIPage = ProtectionMaterialMapper.selectPage(iPage, queryWrapper);
        return userIPage;

    }

    @Override
    public ProtectionMaterial getDetailById(Serializable id) {

        ProtectionMaterial protectionInfo = ProtectionMaterialMapper.selectById(id);
        return protectionInfo;
    }
}
