package com.jwell.classifiedProtection.entry.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwell.classifiedProtection.entry.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 整改建议详情列表
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("biz_rectification_advice_list")
@ApiModel(value = "RectificationAdviceList对象", description = "整改建议详情列表")
public class RectificationAdviceListVo extends BaseEntity<RectificationAdviceListVo> {

    private static final long serialVersionUID = 1L;

    private Integer adviceId;

    private String category;

    private String protectionType;

    @ApiModelProperty(value = "资产id")
    private Integer assetId;

    @ApiModelProperty(value = "资产名称")
    private String assetName;

    private String problem;

    private String risk;

    private String measure;

}
