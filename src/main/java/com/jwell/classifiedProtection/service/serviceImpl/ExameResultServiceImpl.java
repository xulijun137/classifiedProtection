package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.ExameResult;
import com.jwell.classifiedProtection.mapper.ExameResultMapper;
import com.jwell.classifiedProtection.service.IExameResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 评测结果 服务实现类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Service
public class ExameResultServiceImpl extends ServiceImpl<ExameResultMapper, ExameResult> implements IExameResultService {

    @Autowired
    private ExameResultMapper exameResultMapper;

    @Override
    public IPage<ExameResult> paging(Integer pageNum, Integer pageSize,
                                        QueryWrapper<ExameResult> queryWrapper) {

        queryWrapper.lambda().orderByDesc(ExameResult::getCreateTime);

        IPage iPage = new Page(pageNum, pageSize);
        IPage<ExameResult> userIPage = exameResultMapper.selectPage(iPage, queryWrapper);
        return userIPage;

    }

    @Override
    public ExameResult getDetailById(Serializable id) {

        ExameResult protectionInfo = exameResultMapper.selectById(id);
        return protectionInfo;
    }
}
