package com.jwell.classifiedProtection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwell.classifiedProtection.entry.AssessResult;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 评测结果 Mapper 接口
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Repository
public interface AssessResultMapper extends BaseMapper<AssessResult> {

    Integer countAssessResultList(@RequestParam Integer categoryId, @RequestParam String answer);
}
