package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.ArchiveMaterialAudit;
import com.jwell.classifiedProtection.entry.vo.ArchiveMaterialAuditVo;
import com.jwell.classifiedProtection.mapper.ArchiveMaterialAuditMapper;
import com.jwell.classifiedProtection.service.IArchiveMaterialAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 材料审核 服务实现类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Service
public class ArchiveMaterialAuditAuditServiceImpl extends ServiceImpl<ArchiveMaterialAuditMapper, ArchiveMaterialAudit> implements IArchiveMaterialAuditService {

    @Autowired
    private ArchiveMaterialAuditMapper archiveMaterialAuditMapper;

    @Override
    public IPage<ArchiveMaterialAudit> paging(Integer pageNum, Integer pageSize,
                                              QueryWrapper<ArchiveMaterialAudit> queryWrapper) {

        queryWrapper.lambda().orderByDesc(ArchiveMaterialAudit::getCreateTime);

        IPage iPage = new Page(pageNum, pageSize);
        IPage<ArchiveMaterialAudit> userIPage = archiveMaterialAuditMapper.selectPage(iPage, queryWrapper);
        return userIPage;

    }

    @Override
    public IPage<ArchiveMaterialAuditVo> selectVoPage(IPage iPage, ArchiveMaterialAudit archiveMaterialAudit) {

        if (archiveMaterialAudit == null) {
            archiveMaterialAudit = new ArchiveMaterialAudit();
        }
        iPage.setRecords(archiveMaterialAuditMapper.selectVoPage(iPage, archiveMaterialAudit));
        return iPage;
    }

    @Override
    public ArchiveMaterialAudit getDetailById(Serializable id) {

        ArchiveMaterialAudit ArchiveMaterialAudit = archiveMaterialAuditMapper.selectById(id);
        return ArchiveMaterialAudit;
    }

}
