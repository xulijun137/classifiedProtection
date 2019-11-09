package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.ArchiveMaterialList;
import com.jwell.classifiedProtection.entry.vo.ArchiveMaterialListVo;

import java.io.Serializable;

/**
 * <p>
 * 材料审核详情 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
public interface IArchiveMaterialListService extends IService<ArchiveMaterialList> {

    /**
     * 材料审核详情分页列表
     *
     * @return
     */
    IPage<ArchiveMaterialListVo> pagingVo(IPage<ArchiveMaterialListVo> iPage, ArchiveMaterialList archiveMaterialList);

    /**
     * 材料审核任务详情
     *
     * @param id
     * @return
     */
    ArchiveMaterialList getDetailById(Serializable id);

}
