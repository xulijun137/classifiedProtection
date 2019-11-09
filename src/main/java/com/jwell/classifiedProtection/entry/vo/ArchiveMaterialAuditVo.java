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
@TableName("biz_material_audit")
@ApiModel(value = "MaterialAudit对象", description = "材料审核")
public class ArchiveMaterialAuditVo extends BaseEntity<ArchiveMaterialAuditVo> {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    @ApiModelProperty(value = "系统名称")
    private String systemId;

    private String systemName;

    @ApiModelProperty(value = "单位ID，外键")
    private Integer enterpriseId;

    private String enterpriseName;

    @ApiModelProperty(value = "创建人，外键")
    private Integer creatorId;

    private String creatorName;

    @ApiModelProperty(value = "评审状态")
    private String auditState;

}
