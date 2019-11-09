package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.entry.vo.AssessTaskVo;
import com.jwell.classifiedProtection.mapper.AssessTaskMapper;
import com.jwell.classifiedProtection.service.IAssessTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评测结果 服务实现类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Service
public class AssessTaskServiceImpl extends ServiceImpl<AssessTaskMapper, AssessTask> implements IAssessTaskService {

    @Autowired
    private AssessTaskMapper assessTaskMapper;

    @Override
    public IPage<AssessTaskVo> selectAssessTaskVoPaging(IPage<AssessTaskVo> iPage, AssessTask assessTask) {

        IPage<AssessTaskVo> userIPage = assessTaskMapper.selectAssessTaskVoPage(iPage, assessTask);
        return userIPage;

    }
    
}
