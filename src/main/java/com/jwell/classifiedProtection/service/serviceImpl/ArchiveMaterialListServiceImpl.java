package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.ArchiveMaterialList;
import com.jwell.classifiedProtection.entry.vo.ArchiveMaterialListVo;
import com.jwell.classifiedProtection.mapper.ArchiveMaterialListMapper;
import com.jwell.classifiedProtection.service.IArchiveMaterialListService;
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
public class ArchiveMaterialListServiceImpl extends ServiceImpl<ArchiveMaterialListMapper, ArchiveMaterialList> implements IArchiveMaterialListService {

    @Autowired
    private ArchiveMaterialListMapper archiveMaterialListMapper;

    @Override
    public IPage<ArchiveMaterialListVo> pagingVo(IPage<ArchiveMaterialListVo> iPage, ArchiveMaterialList archiveMaterialList) {

        if (archiveMaterialList == null) {
            archiveMaterialList = new ArchiveMaterialList();
        }
        IPage<ArchiveMaterialListVo> pagingVo = archiveMaterialListMapper.pagingVo(iPage, archiveMaterialList);
        return pagingVo;

    }

    @Override
    public ArchiveMaterialList getDetailById(Serializable id) {

        ArchiveMaterialList archiveMaterialList = archiveMaterialListMapper.selectById(id);
        return archiveMaterialList;
    }
}
