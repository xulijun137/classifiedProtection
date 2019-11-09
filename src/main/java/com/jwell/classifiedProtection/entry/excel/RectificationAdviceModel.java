package com.jwell.classifiedProtection.entry.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
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
public class RectificationAdviceModel extends BaseRowModel {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "问题类别", index = 0)
    private String protectionType;

    @ExcelProperty(value = "关联资产", index = 1)
    private String assetName;

    @ExcelProperty(value = "安全问题", index = 2)
    private String problem;

    @ExcelProperty(value = "后果", index = 3)
    private String risk;

    @ExcelProperty(value = "整改措施", index = 4)
    private String measure;

}
