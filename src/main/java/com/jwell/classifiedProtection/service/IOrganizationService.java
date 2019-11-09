package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.Organization;
import com.jwell.classifiedProtection.entry.vo.OrganizationPagingVo;

/**
 * <p>
 * 单位组织 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-22
 */
public interface IOrganizationService extends IService<Organization> {


    /**
     * 单位组织分页列表
     *
     * @param iPage
     * @param organization
     * @return
     */
    IPage<OrganizationPagingVo> selectOrganizationVoPaging(IPage<OrganizationPagingVo> iPage, Organization organization);


}
