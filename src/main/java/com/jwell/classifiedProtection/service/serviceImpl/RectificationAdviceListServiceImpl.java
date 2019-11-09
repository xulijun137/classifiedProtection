package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.RectificationAdviceList;
import com.jwell.classifiedProtection.entry.vo.RectificationAdviceListVo;
import com.jwell.classifiedProtection.mapper.RectificationAdviceListMapper;
import com.jwell.classifiedProtection.service.IRectificationAdviceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 整改建议详情列表 服务实现类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Service
public class RectificationAdviceListServiceImpl extends ServiceImpl<RectificationAdviceListMapper, RectificationAdviceList> implements IRectificationAdviceListService {


    @Autowired
    private RectificationAdviceListMapper rectificationAdviceListMapper;

    @Override
    public IPage<RectificationAdviceListVo> selectRectificationAdviceListVoPage(IPage iPage, RectificationAdviceList rectificationAdviceList) {

        iPage.setRecords(rectificationAdviceListMapper.selectRectificationAdviceListVoPage(iPage, rectificationAdviceList));
        return iPage;

    }

    @Override
    public IPage<RectificationAdviceList> paging(Integer pageNum, Integer pageSize,
                                                 QueryWrapper<RectificationAdviceList> queryWrapper) {

        queryWrapper.lambda().orderByDesc(RectificationAdviceList::getCreateTime);

        IPage iPage = new Page(pageNum, pageSize);
        IPage<RectificationAdviceList> userIPage = rectificationAdviceListMapper.selectPage(iPage, queryWrapper);
        return userIPage;

    }

    @Override
    public RectificationAdviceList getDetailById(Serializable id) {

        RectificationAdviceList rectificationAdviceList = rectificationAdviceListMapper.selectById(id);
        return rectificationAdviceList;
    }
}
