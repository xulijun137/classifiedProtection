package com.jwell.classifiedProtection.entry.dto;

import com.jwell.classifiedProtection.entry.System;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统登记
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-10
 */
@Data
@Accessors(chain = true)
public class SystemDto {

    private System system;

    private Integer taskId;

}
