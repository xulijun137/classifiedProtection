package com.jwell.classifiedProtection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.entry.Enterprise;
import com.jwell.classifiedProtection.entry.vo.EnterpriseVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 企业 Mapper 接口
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Repository
public interface EnterpriseMapper extends BaseMapper<Enterprise> {

    IPage<EnterpriseVo> selectPageVoPage(IPage iPage, @Param("enterprise") Enterprise enterprise);

}
