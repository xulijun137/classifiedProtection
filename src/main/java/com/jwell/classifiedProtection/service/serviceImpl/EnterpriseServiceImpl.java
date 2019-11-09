package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.Enterprise;
import com.jwell.classifiedProtection.entry.vo.EnterpriseVo;
import com.jwell.classifiedProtection.mapper.EnterpriseMapper;
import com.jwell.classifiedProtection.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 企业 服务实现类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements IEnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public IPage<EnterpriseVo> selectEnterpriseVoPaging(IPage<EnterpriseVo> iPage, Enterprise enterprise) {

        IPage<EnterpriseVo> userIPage = enterpriseMapper.selectPageVoPage(iPage, enterprise);
        return userIPage;

    }

    @Override
    public Enterprise getDetailById(Serializable id) {

        Enterprise asset = enterpriseMapper.selectById(id);
        return asset;
    }
}
