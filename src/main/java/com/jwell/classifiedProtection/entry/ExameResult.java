package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评测结果
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("biz_exame_result")
public class ExameResult extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String questionId;

    /**
     * 现状描述
     */
    private String presentSituation;

    private String answer;

}
