package com.jwell.classifiedProtection.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.exceptions.InvalidException;
import com.jwell.classifiedProtection.commons.utils.SMSUtil;
import com.jwell.classifiedProtection.commons.utils.VerifyCodeUtils;
import com.jwell.classifiedProtection.components.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证码请求
 */
@Slf4j
@Api(value = "验证码接口集合", tags = "验证码接口集合")
@RestController
@RequestMapping(value = "/common/code")
public class CodeController extends BaseController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SMSUtil smsUtil;

    @ApiOperation(value = "获取图片验证码", notes = "获取图片验证码")
    @RequestMapping(value = "/imgCode", name = "name", method = RequestMethod.GET)
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
            }
    )
    public void getImgCode(HttpServletRequest request, HttpServletResponse response) {

        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();
        //以秒为单位，即在没有活动30分钟后，session将失效
        session.setMaxInactiveInterval(30 * 60);

        //调用工具类生成验证码
        String verifyCode = VerifyCodeUtils.generateNumberVerifyCode(4);
        LOGGER.info("verifyCode----------" + verifyCode);
        //session.setAttribute("code", verifyCode);
        //保存到redis,并设置时长为10分钟
        redisUtil.set(verifyCode, verifyCode, 10 * 60);

        try {
            VerifyCodeUtils.outputImage(100, 38, response.getOutputStream(), verifyCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "获取短信验证码", notes = "获取短信验证码")
    @RequestMapping(value = "/smsCode", name = "name", method = RequestMethod.GET)
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "query", name = "phone", value = "电话号码", dataType = "String"),
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
            }
    )
    public ResultObject sendSMSVerifyCode(String phone) {

        ResultObject resultObject = new ResultObject();
        //调用工具类生成验证码
        String verifyCode = SMSUtil.getVerifyCode4();
        log.info("短信验证码是：{}", verifyCode);

        JSONObject jsonObject = smsUtil.sendVerifyCode(phone, verifyCode);
        if (jsonObject != null) {
            log.info(jsonObject.toJSONString());

            if (jsonObject.getBoolean("success")) {
                //放入缓存
                redisUtil.set(phone, verifyCode, 5 * 60);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setMsg("短信验证码发送成功！");
                log.info("短信验证码发送成功！");

            } else {
                log.info("短信验证码发送失败！{}", jsonObject.getString("msg"));
                resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resultObject.setMsg("短信验证码发送失败:" + jsonObject.getString("msg"));

            }
        } else {
            log.info("短信接口请求异常！短信接口返回空");
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultObject.setMsg("短信验证码发送失败，短信接口返回空");

        }
        return resultObject;
    }

    @ApiIgnore
    @ApiOperation(value = "测试", notes = "测试接口")
    @RequestMapping(value = "/test", name = "name", method = RequestMethod.GET)
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
            }
    )
    public ResultObject test(HttpServletRequest request, HttpServletResponse response)
            throws InvalidException{

        //int a = 10/0;
        throw new InvalidException(HttpServletResponse.SC_BAD_REQUEST,"这是一个我自己定义的异常格式异常");

    }
}