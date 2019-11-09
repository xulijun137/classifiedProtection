package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 关联资产
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("biz_asset")
public class Asset extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String assetName;

    private String ip;

    private String position;

    /**
     * 资产类型
     */
    private String assetType;

    private String categoryId;

    /**
     * 等保等级
     */
    private String level;

    private String description;
}
