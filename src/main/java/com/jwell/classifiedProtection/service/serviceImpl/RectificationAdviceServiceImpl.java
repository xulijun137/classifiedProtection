package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.RectificationAdvice;
import com.jwell.classifiedProtection.entry.vo.RectificationAdviceVo;
import com.jwell.classifiedProtection.mapper.RectificationAdviceMapper;
import com.jwell.classifiedProtection.service.IRectificationAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 整改建议 服务实现类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Service
public class RectificationAdviceServiceImpl extends ServiceImpl<RectificationAdviceMapper, RectificationAdvice> implements IRectificationAdviceService {

    @Autowired
    private RectificationAdviceMapper rectificationAdviceMapper;

    @Override
    public IPage<RectificationAdviceVo> selectRectificationAdviceVoPage(IPage iPage, RectificationAdvice rectificationAdvice) {

        iPage.setRecords(rectificationAdviceMapper.selectRectificationAdviceVoPage(iPage, rectificationAdvice));
        return iPage;

    }

    @Override
    public RectificationAdvice getDetailById(Serializable id) {

        RectificationAdvice RectificationAdvice = rectificationAdviceMapper.selectById(id);
        return RectificationAdvice;
    }
}
