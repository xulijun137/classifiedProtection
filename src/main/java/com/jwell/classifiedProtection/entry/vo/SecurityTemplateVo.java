package com.jwell.classifiedProtection.entry.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwell.classifiedProtection.entry.BaseEntity;
import com.jwell.classifiedProtection.entry.FileBank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 安全制度模板
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("biz_security_template")
public class SecurityTemplateVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String title;

    private String enterpriseName;

    private String fileName;

    //文件的网络地址
    private String uri;

    //实际文件存储的路径
    private String path;

    private byte[] fileBlob;

    private String type;

    private Integer userId;

    private FileBank fileBank;
}
