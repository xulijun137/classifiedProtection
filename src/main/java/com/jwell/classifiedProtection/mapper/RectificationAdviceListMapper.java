package com.jwell.classifiedProtection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.entry.RectificationAdviceList;
import com.jwell.classifiedProtection.entry.vo.RectificationAdviceListVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 整改建议详情列表 Mapper 接口
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Repository
public interface RectificationAdviceListMapper extends BaseMapper<RectificationAdviceList> {

    List<RectificationAdviceListVo> selectRectificationAdviceListVoPage(
            IPage iPage, @Param("rectificationAdviceList") RectificationAdviceList rectificationAdviceList);

}
