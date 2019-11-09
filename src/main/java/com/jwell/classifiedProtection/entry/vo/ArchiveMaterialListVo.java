package com.jwell.classifiedProtection.entry.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwell.classifiedProtection.entry.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 材料审核
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("biz_archive_material_list")
@ApiModel(value = "ArchiveMaterial对象", description = "备案材料")
public class ArchiveMaterialListVo extends BaseEntity<ArchiveMaterialListVo> {

    private static final long serialVersionUID = 1L;

    private Integer auditId;

    private String materialName;

    @ApiModelProperty(value = "材料类型")
    private String materialType;

    private String fileName;

    private Integer fileId;

    private byte[] fileBlob;

    @ApiModelProperty(value = "材料来源")
    private String materialFrom;

    @ApiModelProperty(value = "材料名")
    private String finishState;

    private String auditState;


}
