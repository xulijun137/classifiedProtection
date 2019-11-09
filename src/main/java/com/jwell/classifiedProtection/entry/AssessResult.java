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
@TableName("biz_assess_result")
@ApiModel(value = "AssessResult对象", description = "评测结果")
public class AssessResult extends BaseEntity<AssessResult> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "用户ID")
    private Integer userId;

    @ApiModelProperty(name = "评测任务ID")
    private Integer taskId;

    @ApiModelProperty(name = "类型ID")
    private Integer categoryId;

    @ApiModelProperty(name = "资产ID")
    private Integer assetId;

    @ApiModelProperty(name = "问题ID")
    private Integer questionId;

    @ApiModelProperty(name = "答案")
    private String answer;

    @ApiModelProperty(name = "现状描述")
    private String description;

}
