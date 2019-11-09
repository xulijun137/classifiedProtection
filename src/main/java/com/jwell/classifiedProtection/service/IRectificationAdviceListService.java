package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.RectificationAdviceList;
import com.jwell.classifiedProtection.entry.vo.RectificationAdviceListVo;

import java.io.Serializable;

/**
 * <p>
 * 整改建议详情详情列表 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
public interface IRectificationAdviceListService extends IService<RectificationAdviceList> {

    /**
     * 整改建议详情分页列表
     *
     * @param queryWrapper
     * @return
     */
    IPage<RectificationAdviceList> paging(Integer pageNum, Integer pageSize,
                                          QueryWrapper<RectificationAdviceList> queryWrapper);

    /**
     * 整改建议详情联表查询分页
     *
     * @param iPage
     * @param rectificationAdviceList
     * @return
     */
    IPage<RectificationAdviceListVo> selectRectificationAdviceListVoPage(IPage iPage, RectificationAdviceList rectificationAdviceList);

    /**
     * 整改建议详情详情
     *
     * @param id
     * @return
     */
    RectificationAdviceList getDetailById(Serializable id);

}
