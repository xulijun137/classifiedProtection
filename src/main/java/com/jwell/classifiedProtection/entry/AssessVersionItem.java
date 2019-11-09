package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
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
@TableName("biz_assess_version_item")
@ApiModel(value = "AssessVersionItem对象", description = "评测结果")
public class AssessVersionItem extends BaseEntity<AssessVersionItem> {

    private static final long serialVersionUID = 1L;

    private String version;

    private Integer order;

    private String assessItem;


}
