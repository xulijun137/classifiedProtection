package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 存储附件的数据表
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_file_bank")
@ApiModel(value = "FileBank对象", description = "存储附件的数据表")
public class FileBank extends BaseEntity<FileBank> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "相对路径")
    private String filePath;

    private String tableName;

    @ApiModelProperty(value = "外键ID")
    private Integer foreignId;

    private String fileType;

    @ApiModelProperty(value = "附件")
    private byte[] fileBlob;

    @ApiModelProperty(value = "备注、描述")
    private String description;

}
