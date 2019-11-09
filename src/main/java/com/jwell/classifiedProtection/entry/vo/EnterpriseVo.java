package com.jwell.classifiedProtection.entry.vo;

import com.jwell.classifiedProtection.entry.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class EnterpriseVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 上级单位（企业）
     */
    private Integer parentId;

    /**
     * 上级单位名称
     */
    private String parentName;

    /**
     * 单位（企业）名称
     */
    private String enterpriseName;

    /**
     * 备注
     */
    private String remark;

}
