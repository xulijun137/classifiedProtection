package com.jwell.classifiedProtection.entry.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwell.classifiedProtection.entry.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 整改建议
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("biz_rectification_advice")
public class RectificationAdviceVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产
     */
    private Integer systemId;

    private String systemName;

    private Integer enterpriseId;

    private String enterpriseName;

    private Integer creatorId;

    private String creatorName;

    private String progressState;

}
