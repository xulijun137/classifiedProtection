package com.jwell.classifiedProtection.entry.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwell.classifiedProtection.entry.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 单位组织
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_organization")
@ApiModel(value = "Organization对象", description = "单位组织")
public class OrganizationDetailVo extends BaseEntity<OrganizationDetailVo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "上级单位（企业）")
    private Integer pid;

    @ApiModelProperty(value = "上级单位（企业）")
    private Integer userId;

    @ApiModelProperty(value = "单位（企业）名称")
    private String organizationName;

    @ApiModelProperty(value = "营业执照")
    private Integer businessLicenseId;

    @ApiModelProperty(value = "单位地址")
    private String address;

    @ApiModelProperty(value = "邮政编码")
    private String postalCode;

    @ApiModelProperty(value = "行政代码")
    private String adminCode;

    @ApiModelProperty(value = "负责人姓名")
    private String chargerName;

    @ApiModelProperty(value = "负责人职位")
    private String chargerPosition;

    @ApiModelProperty(value = "负责人电话")
    private String chargerPhone;

    @ApiModelProperty(value = "负责人邮箱")
    private String chargerEmail;

    @ApiModelProperty(value = "负责人证件")
    private Integer chargerLicenseId;

    @ApiModelProperty(value = "负责人责任部门")
    private String chargerDepartment;

    @ApiModelProperty(value = "联系人姓名")
    private String contactsName;

    @ApiModelProperty(value = "联系人职位")
    private String contactsPosition;

    @ApiModelProperty(value = "联系人电话")
    private String contactsPhone;

    @ApiModelProperty(value = "联系人办公电话")
    private String contactsMobile;

    @ApiModelProperty(value = "联系人邮箱")
    private String contactsEmail;

    @ApiModelProperty(value = "联系人负责部门")
    private String contactsDepartment;

    @ApiModelProperty(value = "安全员证书")
    private Integer officerLicenseId;

    @ApiModelProperty(value = "从属关系")
    private String affiliation;

    @ApiModelProperty(value = "组织类型")
    private String organizationType;

    @ApiModelProperty(value = "行业类别")
    private String industryType;

    @ApiModelProperty(value = "信息系统数量")
    private Integer infoSysNums;

    @ApiModelProperty(value = "二级系统数量")
    private Integer secondSysNums;

    @ApiModelProperty(value = "三级系统数量")
    private Integer thirdSysNums;

    @ApiModelProperty(value = "四级系统数量")
    private Integer fouthSysNums;

    @ApiModelProperty(value = "五级系统数量")
    private Integer fifthSysNums;

    @ApiModelProperty(value = "六级系统数量")
    private Integer sixthSysNums;

}
