package com.jwell.classifiedProtection.commons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value="统一返回对象")
public class ResultObject<T> implements Serializable {

    @ApiModelProperty(value="返回的消息")
    private String msg ;

    @ApiModelProperty(value="返回的状态码")
    private Integer code;

    @ApiModelProperty(value="返回成功或者失败标志")
    private Boolean success;

    @ApiModelProperty(value="返回数据集合")
    private T data;

    public static ResultObject success() {
        ResultObject resultObject = new ResultObject();
        resultObject.setSuccess(Boolean.TRUE);
        resultObject.setCode(HttpServletResponse.SC_OK);
        resultObject.setMsg("接口返回成功！");
        return resultObject;
    }

    public static ResultObject failure() {
        ResultObject resultObject = new ResultObject();
        resultObject.setSuccess(Boolean.FALSE);
        resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resultObject.setMsg("接口返回失败！");
        return resultObject;
    }
}
