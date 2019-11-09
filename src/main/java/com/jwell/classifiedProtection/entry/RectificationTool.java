package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("biz_rectification_tool")
public class RectificationTool extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String toolName;

    /**
     * 标题
     */
    private String fileName;

    /**
     * 工具分类
     */
    private String type;

    /**
     * 作者
     */
    private String author;

    /**
     * 路径位置
     */
    private String uri;

    /**
     * Base64字符串
     */
    private byte[] fileBlob;

    /**
     * 简介描述
     */
    private String description;

}
