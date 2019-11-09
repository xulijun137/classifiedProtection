package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 安全技术工具
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_security_tool")
@ApiModel(value = "SecurityTool对象", description = "安全技术工具")
public class SecurityTool extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String toolName;

    private String type;

    private String fileName;

    private String description;

}
