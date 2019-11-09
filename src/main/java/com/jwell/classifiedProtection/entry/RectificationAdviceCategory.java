package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_rectification_advice_category")
@ApiModel(value = "RectificationAdviceCategory对象", description = "")
public class RectificationAdviceCategory extends BaseEntity<RectificationAdviceCategory> {

    private static final long serialVersionUID = 1L;

    private Integer parentId;

    private String parentLevel;

    private String requirement;

    private String maxLevel;

    private String version;

    private String constraintAssetType;

}
