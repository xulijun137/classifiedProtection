package com.jwell.classifiedProtection.commons.exceptions;

import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.formula.functions.T;

public class InvalidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="返回的消息")
    private String msg ;

    @ApiModelProperty(value="返回的状态码")
    private Integer code;

    @ApiModelProperty(value="返回成功或者失败标志")
    private Boolean success;

    @ApiModelProperty(value="返回数据集合")
    private T data;

    public InvalidException() {}

    public InvalidException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public InvalidException(String msg, Integer code){
        this.msg = msg;
        this.code = code;
    }

    public InvalidException(String msg, Integer code, Boolean success){
        this.msg = msg;
        this.code = code;
        this.success = success;
    }

}
