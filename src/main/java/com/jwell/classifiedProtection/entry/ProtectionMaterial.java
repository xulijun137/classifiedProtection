package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 等保资料
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("biz_protection_material")
public class ProtectionMaterial extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String title;

    private String author;

    private String type;

    private String abstracts;

}
