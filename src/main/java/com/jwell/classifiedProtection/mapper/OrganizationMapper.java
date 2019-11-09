package com.jwell.classifiedProtection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.entry.Organization;
import com.jwell.classifiedProtection.entry.vo.OrganizationPagingVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 单位组织 Mapper 接口
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-22
 */
@Repository
public interface OrganizationMapper extends BaseMapper<Organization> {

    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * 注意!!: 如果入参是有多个,需要加注解指定参数名才能在xml中取值
     * </p>
     *
     * @param iPage        分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param organization 关键字
     * @return 分页对象
     */
    IPage<OrganizationPagingVo> selectOrganizationVoPage(IPage iPage, @Param("Organization") Organization organization);
    
}
