package com.jwell.classifiedProtection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.entry.ArchiveMaterialAudit;
import com.jwell.classifiedProtection.entry.vo.ArchiveMaterialAuditVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 材料审核 Mapper 接口
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Repository
public interface ArchiveMaterialAuditMapper extends BaseMapper<ArchiveMaterialAudit> {


    List<ArchiveMaterialAuditVo> selectVoPage(
            IPage iPage, ArchiveMaterialAudit archiveMaterialAudit);
}
