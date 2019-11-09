package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 测评问题库
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_question_bank")
@ApiModel(value = "QuestionBank对象", description = "测评问题库")
public class QuestionBank extends BaseEntity<QuestionBank> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资产ID")
    private Integer assetId;

    @ApiModelProperty(value = "编号")
    private Integer questionOrder;

    @ApiModelProperty(value = "题目")
    private String questionName;

    @ApiModelProperty(value = "问题类型")
    private String questionType;

    @ApiModelProperty(value = "选项A")
    private String optionA;

    @ApiModelProperty(value = "选项B")
    private String optionB;

    @ApiModelProperty(value = "选项C")
    private String optionC;

    @ApiModelProperty(value = "选项D")
    private String optionD;

    @ApiModelProperty(value = "正确答案")
    private String rightAnswer;

    @ApiModelProperty(value = "题目说明")
    private String description;

}
