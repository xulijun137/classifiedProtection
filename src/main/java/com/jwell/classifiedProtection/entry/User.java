package com.jwell.classifiedProtection.entry;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "用户名", index = 0)
    private String userName;

    @ExcelProperty(value = "密码", index = 1)
    private String password;

    @ExcelProperty(value = "真实姓名", index = 2)
    private String realName;

    @ExcelProperty(value = "电话号码", index = 3)
    private String phone;

    @ExcelProperty(value = "邮箱", index = 4)
    private String email;

    private String roleType;

    private Integer enterpriseId;

    private String remark;

}
