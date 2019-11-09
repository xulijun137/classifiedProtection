package com.jwell.classifiedProtection.entry.dto;

import com.jwell.classifiedProtection.entry.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String code;

    private String passwordconfirm;

    private User user;
}
