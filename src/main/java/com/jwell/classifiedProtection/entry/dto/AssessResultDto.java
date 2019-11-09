package com.jwell.classifiedProtection.entry.dto;

import com.jwell.classifiedProtection.entry.AssessResult;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 评测结果
 * </p>
 *
 * @author RonnieXu
 * @since 2019-09-06
 */
@Data
@Accessors(chain = true)
public class AssessResultDto {

    private static final long serialVersionUID = 1L;

    private AssessResult assessResult;

    private MultipartFile[] multipartFiles;

}
