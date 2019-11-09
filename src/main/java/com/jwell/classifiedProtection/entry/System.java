package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统登记
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_system")
@ApiModel(value = "System对象", description = "系统登记")
public class System extends BaseEntity<System> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "外键，公司ID")
    private Integer enterpriseId;

    @ApiModelProperty(value = "系统名称")
    private String systemName;

    @ApiModelProperty(value = "系统编号")
    private String sysremNumber;

    @ApiModelProperty(value = "业务类型")
    private String businessType;

    @ApiModelProperty(value = "业务描述")
    private String businessDesc;

    @ApiModelProperty(value = "业务范围")
    private String serviceRange;

    @ApiModelProperty(value = "服务对象")
    private String serviceObject;

    @ApiModelProperty(value = "系统互联情况")
    private String systemConncting;

    @ApiModelProperty(value = "安全产品")
    private String productSecurity;

    @ApiModelProperty(value = "网络产品")
    private String productNetwork;

    @ApiModelProperty(value = "操作系统")
    private String productOs;

    @ApiModelProperty(value = "数据库")
    private String productDb;

    @ApiModelProperty(value = "服务器")
    private String productServer;

    @ApiModelProperty(value = "其他")
    private String productOther;

    @ApiModelProperty(value = "等级测评")
    private String serviceGrade;

    @ApiModelProperty(value = "风险评估")
    private String serviceRisk;

    @ApiModelProperty(value = "灾难恢复")
    private String serviceDisaster;

    @ApiModelProperty(value = "应急响应")
    private String serviceEmergency;

    @ApiModelProperty(value = "系统集成")
    private String serviceIntegration;

    @ApiModelProperty(value = "安全咨询")
    private String serviceConsultation;

    @ApiModelProperty(value = "安全培训")
    private String serviceTraining;

    @ApiModelProperty(value = "其他")
    private String serviceOther;

    @ApiModelProperty(value = "系统承载情况")
    private String systemBearer;

    @ApiModelProperty(value = "开始运行时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beginRunningTime;

    @ApiModelProperty(value = "是否是子系统")
    private String isSubsystem;

    @ApiModelProperty(value = "父系统")
    private String superSystem;

    @ApiModelProperty(value = "父系统单位")
    private String superSystemDepartment;


}
