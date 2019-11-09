package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.QuestionBank;
import com.jwell.classifiedProtection.mapper.QuestionBankMapper;
import com.jwell.classifiedProtection.service.IQuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 测评问题库 服务实现类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements IQuestionBankService {

    @Autowired
    private QuestionBankMapper questionBankMapper;

    @Override
    public IPage<QuestionBank> paging(Integer pageNum, Integer pageSize,
                                      QueryWrapper<QuestionBank> queryWrapper) {
        IPage iPage = new Page(pageNum, pageSize);
        IPage<QuestionBank> userIPage = questionBankMapper.selectPage(iPage, queryWrapper);
        return userIPage;

    }

    @Override
    public QuestionBank getDetailById(Serializable id) {

        QuestionBank questionBank = questionBankMapper.selectById(id);
        return questionBank;
    }
}
