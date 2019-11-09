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
 * 评测结果
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_assess_task")
@ApiModel(value = "AssessTask对象", description = "评测结果")
public class AssessTaskVo extends BaseEntity<AssessTaskVo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "系统ID")
    private Integer systemId;

    @ApiModelProperty(value = "系统名称")
    private String systemName;

    @ApiModelProperty(value = "单位组织ID")
    private Integer organizationId;

    @ApiModelProperty(value = "单位组织名称")
    private String organizationName;

    @ApiModelProperty(value = "评测进度ID")
    private Integer progressId;

    @ApiModelProperty(value = "评测状态")
    private String progressState;

    @ApiModelProperty(value = "评测结果")
    private String result;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

}
