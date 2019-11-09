package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 关联资产
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_asset_category")
@ApiModel(value = "AssetCategory对象", description = "关联资产")
public class AssetCategory extends BaseEntity<AssetCategory> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "相关类型")
    private String categoryName;

    private String version;

    private String description;

}
