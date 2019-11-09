package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("biz_archive_material_audit")
@ApiModel(value = "ArchiveMaterialAudit对象", description = "材料审核")
public class ArchiveMaterialAudit extends BaseEntity<ArchiveMaterialAudit> {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    @ApiModelProperty(value = "系统名称")
    private Integer systemId;

    @ApiModelProperty(value = "单位ID，外键")
    private Integer enterpriseId;

    @ApiModelProperty(value = "创建人，外键")
    private Integer creatorId;

    private Integer creatorName;

    @ApiModelProperty(value = "评审状态")
    private String auditState;

}
