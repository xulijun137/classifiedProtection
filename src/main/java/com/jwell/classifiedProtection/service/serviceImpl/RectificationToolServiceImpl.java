package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.service.IRectificationToolService;
import com.jwell.classifiedProtection.entry.RectificationTool;
import com.jwell.classifiedProtection.mapper.RectificationToolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 系统 服务实现类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-05
 */
@Service
public class RectificationToolServiceImpl extends ServiceImpl<RectificationToolMapper, RectificationTool> implements IRectificationToolService {

    @Autowired
    private RectificationToolMapper rectificationToolMapper;

    @Override
    public IPage<RectificationTool> paging(Integer pageNum, Integer pageSize,
                                           QueryWrapper<RectificationTool> queryWrapper) {

        queryWrapper.lambda().orderByDesc(RectificationTool::getCreateTime);

        IPage iPage = new Page(pageNum, pageSize);
        IPage<RectificationTool> userIPage = rectificationToolMapper.selectPage(iPage, queryWrapper);
        return userIPage;

    }

    @Override
    public RectificationTool getDetailById(Serializable id) {

        RectificationTool rectificationTool = rectificationToolMapper.selectById(id);
        return rectificationTool;
    }

}
