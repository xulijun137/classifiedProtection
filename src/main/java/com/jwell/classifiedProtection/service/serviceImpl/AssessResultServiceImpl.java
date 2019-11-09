package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.AssessResult;
import com.jwell.classifiedProtection.mapper.AssessResultMapper;
import com.jwell.classifiedProtection.service.IAssessResultService;
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
public class AssessResultServiceImpl extends ServiceImpl<AssessResultMapper, AssessResult> implements IAssessResultService {

    @Autowired
    private AssessResultMapper assessResultMapper;

    @Override
    public Integer countAssessResultList(Integer categoryId, String answer) {

        return assessResultMapper.countAssessResultList(categoryId, answer);
    }
}
