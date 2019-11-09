package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.RectificationAdvice;
import com.jwell.classifiedProtection.entry.vo.RectificationAdviceVo;

import java.io.Serializable;

/**
 * <p>
 * 整改建议任务 服务类
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
public interface IRectificationAdviceService extends IService<RectificationAdvice> {

    /**
     * 整改建议任务分页列表
     * @param iPage
     * @param rectificationAdvice
     * @return
     */
    IPage<RectificationAdviceVo> selectRectificationAdviceVoPage(IPage iPage, RectificationAdvice rectificationAdvice);

    /**
     * 整改建议任务详情
     * @param id
     * @return
     */
    RectificationAdvice getDetailById(Serializable id);
}
