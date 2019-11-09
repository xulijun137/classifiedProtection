package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class AssessTask extends BaseEntity<AssessTask> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "系统ID")
    private Integer systemId;

    @ApiModelProperty(value = "单位组织ID")
    private Integer organizationId;

    @ApiModelProperty(value = "系统定级ID")
    private Integer gradingId;

    @ApiModelProperty(value = "备案材料ID")
    private Integer materialListId;

    @ApiModelProperty(value = "评测进度ID")
    private Integer progressId;

    @ApiModelProperty(value = "评测状态")
    private String progressState;

    @ApiModelProperty(value = "评测结果")
    private String result;

    @ApiModelProperty(value = "关联的资产ID")
    private Integer assetId;


}
