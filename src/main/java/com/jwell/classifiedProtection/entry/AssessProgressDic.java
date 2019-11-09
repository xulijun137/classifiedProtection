package com.jwell.classifiedProtection.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评测进度(字典表)
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("biz_assess_progress_dic")
@ApiModel(value = "AssessProgressDic对象", description = "评测进度(字典表)")
public class AssessProgressDic extends BaseEntity<AssessProgressDic> {

    private static final long serialVersionUID = 1L;

    private String progressName;

}
