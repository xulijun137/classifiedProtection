package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 企业
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("biz_enterprise")
public class Enterprise extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 上级单位（企业）
     */
    private Integer parentId;

    /**
     * 单位（企业）名称
     */
    private String enterpriseName;

    /**
     * 备注
     */
    private String remark;

}
