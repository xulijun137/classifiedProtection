package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class RectificationAdviceList extends BaseEntity<RectificationAdviceList> {

    private static final long serialVersionUID = 1L;

    private Integer adviceId;

    private String protectionType;

    private String category;

    private Integer categoryId;

    @ApiModelProperty(value = "资产id")
    private Integer assetId;

    @ApiModelProperty(value = "资产名称")
    private String assetName;

    @ApiModelProperty(value = "现状描述")
    private String currentDescription;

    private String problem;

    private String risk;

    private String measure;

}
