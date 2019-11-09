package com.jwell.classifiedProtection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.entry.AssessTask;
import com.jwell.classifiedProtection.entry.vo.AssessTaskVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 评测结果 Mapper 接口
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Repository
public interface AssessTaskMapper extends BaseMapper<AssessTask> {

    IPage<AssessTaskVo> selectAssessTaskVoPage(IPage iPage, @Param("AssessTask") AssessTask assessTask);
}
