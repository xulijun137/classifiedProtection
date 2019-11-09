package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.ArchiveMaterialAudit;
import com.jwell.classifiedProtection.entry.vo.ArchiveMaterialAuditVo;

import java.io.Serializable;

/**
 * <p>
 * 材料审核任务 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
public interface IArchiveMaterialAuditService extends IService<ArchiveMaterialAudit> {

    /**
     * 材料审核任务分页列表
     *
     * @param queryWrapper
     * @return
     */
    IPage<ArchiveMaterialAudit> paging(Integer pageNum, Integer pageSize,
                                       QueryWrapper<ArchiveMaterialAudit> queryWrapper);

    /**
     * 整改建议任务分页列表
     *
     * @param iPage
     * @param archiveMaterialAudit
     * @return
     */
    IPage<ArchiveMaterialAuditVo> selectVoPage(IPage iPage, ArchiveMaterialAudit archiveMaterialAudit);

    /**
     * 材料审核任务详情
     *
     * @param id
     * @return
     */
    ArchiveMaterialAudit getDetailById(Serializable id);

}
