package com.jwell.classifiedProtection.entry.vo;

import com.jwell.classifiedProtection.entry.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserVo extends BaseEntity {

    private Integer age;

    private String username;

    private String password;

    private String realName;

    private Integer areaCode;

    private String email;

    private String phoneNum;

    private String areaName;

    private Integer roleId;

    private String remark;


}
