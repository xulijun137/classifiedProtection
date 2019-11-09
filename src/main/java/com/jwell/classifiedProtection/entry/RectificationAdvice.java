package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class RectificationAdvice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产
     */
    private Integer systemId;

    private Integer enterpriseId;

    private Integer creatorId;

    private String progressState;

}
