package com.jwell.classifiedProtection.entry.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jwell.classifiedProtection.entry.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统定级
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_system_grading")
@ApiModel(value = "SystemGrading对象", description = "系统定级")
public class SystemGradingVo extends BaseEntity<SystemGradingVo> {

    private static final long serialVersionUID = 1L;

    private Integer taskId;

    @ApiModelProperty(value = "业务信息（多选）")
    private String businessInformation;

    @ApiModelProperty(value = "系统服务（多选）")
    private String systemService;

    @ApiModelProperty(value = "等保标准")
    private String protecitonStandard;

    @ApiModelProperty(value = "安全类型（多选）")
    private String securityType;

    @ApiModelProperty(value = "定级时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime gradingTime;

    @ApiModelProperty(value = "专家评定状况")
    private String expertState;

    @ApiModelProperty(value = "专家定级文件")
    private String expertFileName;

    @ApiModelProperty(value = "专家定级文件ID")
    private Integer expertFileId;

    @ApiModelProperty(value = "是否有主管部门")
    private String competentDepartment;

    @ApiModelProperty(value = "主管部门名称")
    private String departmentName;

    @ApiModelProperty(value = "主管部门定级情况")
    private String departmentState;

    @ApiModelProperty(value = "主管部门定级情况文件名称")
    private String departmentFileName;

    @ApiModelProperty(value = "主管部门定级情况文件ID")
    private Integer departmentFileId;

    @ApiModelProperty(value = "系统定级报告(有无）")
    private String gradingReport;

    @ApiModelProperty(value = "系统定级报告文件名称")
    private String gradingFileName;

    @ApiModelProperty(value = "系统定级报告文件Id")
    private Integer gradingFileId;

    @ApiModelProperty(value = "填表人")
    private String fillingPerson;

    @ApiModelProperty(value = "填表时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime fillingTime;

}
