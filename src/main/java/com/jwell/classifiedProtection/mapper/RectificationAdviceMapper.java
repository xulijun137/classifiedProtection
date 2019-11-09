package com.jwell.classifiedProtection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.entry.RectificationAdvice;
import com.jwell.classifiedProtection.entry.vo.RectificationAdviceVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 整改建议 Mapper 接口
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Repository
public interface RectificationAdviceMapper extends BaseMapper<RectificationAdvice> {

    List<RectificationAdviceVo> selectRectificationAdviceVoPage(
            IPage iPage, @Param("rectificationAdvice") RectificationAdvice rectificationAdvice);
}
