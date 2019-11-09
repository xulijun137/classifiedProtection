package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.Organization;
import com.jwell.classifiedProtection.entry.vo.OrganizationPagingVo;
import com.jwell.classifiedProtection.mapper.OrganizationMapper;
import com.jwell.classifiedProtection.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 单位组织 服务实现类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-22
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements IOrganizationService {


    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public IPage<OrganizationPagingVo> selectOrganizationVoPaging(IPage<OrganizationPagingVo> iPage, Organization Organization) {

        IPage<OrganizationPagingVo> userIPage = organizationMapper.selectOrganizationVoPage(iPage, Organization);
        return userIPage;

    }
}
