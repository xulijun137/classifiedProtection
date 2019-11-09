package com.jwell.classifiedProtection.entry.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwell.classifiedProtection.entry.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 单位组织
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_organization")
@ApiModel(value = "Organization对象", description = "单位组织")
public class OrganizationPagingVo extends BaseEntity<OrganizationPagingVo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单位（企业）名称")
    private String organizationName;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

}
