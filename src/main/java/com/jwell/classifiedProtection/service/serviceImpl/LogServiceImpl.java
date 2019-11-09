package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.Log;
import com.jwell.classifiedProtection.mapper.LogMapper;
import com.jwell.classifiedProtection.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 系统等级 服务实现类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-08
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public IPage<Log> paging(Integer pageNum, Integer pageSize,
                                           QueryWrapper<Log> queryWrapper) {

        queryWrapper.lambda().orderByDesc(Log::getCreateTime);

        IPage iPage = new Page(pageNum, pageSize);
        IPage<Log> userIPage = logMapper.selectPage(iPage, queryWrapper);
        return userIPage;

    }

    @Override
    public Log getDetailById(Serializable id) {

        Log log = logMapper.selectById(id);
        return log;
    }

}
