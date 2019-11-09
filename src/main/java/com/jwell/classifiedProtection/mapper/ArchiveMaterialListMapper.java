package com.jwell.classifiedProtection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.entry.ArchiveMaterialList;
import com.jwell.classifiedProtection.entry.vo.ArchiveMaterialListVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 材料审核 Mapper 接口
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Repository
public interface ArchiveMaterialListMapper extends BaseMapper<ArchiveMaterialList> {

    IPage<ArchiveMaterialListVo> pagingVo(IPage iPage, @Param("archiveMaterialList") ArchiveMaterialList archiveMaterialList);


}
