package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.SecurityTemplate;
import com.jwell.classifiedProtection.mapper.SecurityTemplateMapper;
import com.jwell.classifiedProtection.service.ISecurityTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 安全制度模板 服务实现类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-07
 */
@Service
public class SecurityTemplateServiceImpl extends ServiceImpl<SecurityTemplateMapper, SecurityTemplate> implements ISecurityTemplateService {

    @Autowired
    private SecurityTemplateMapper securityTemplateMapper;

    @Override
    public IPage<SecurityTemplate> paging(Integer pageNum, Integer pageSize,
                                           QueryWrapper<SecurityTemplate> queryWrapper) {

        queryWrapper.lambda().orderByDesc(SecurityTemplate::getCreateTime);

        IPage iPage = new Page(pageNum, pageSize);
        IPage<SecurityTemplate> userIPage = securityTemplateMapper.selectPage(iPage, queryWrapper);
        return userIPage;

    }

    @Override
    public SecurityTemplate getDetailById(Serializable id) {

        SecurityTemplate rectificationTool = securityTemplateMapper.selectById(id);
        return rectificationTool;
    }

}
