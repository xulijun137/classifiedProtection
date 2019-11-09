package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评测项
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_assess_item")
@ApiModel(value = "AssessItem对象", description = "评测项")
public class AssessItem extends BaseEntity<AssessItem> {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer systemId;

    private Integer enterpriseId;

    private Integer progressId;

    private String state;

}
