package com.jwell.classifiedProtection.interceptors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.enums.ResponseCodeEnum;
import com.jwell.classifiedProtection.commons.utils.JwtUtils;
import com.jwell.classifiedProtection.commons.utils.ThreadLocalUtil;
import com.jwell.classifiedProtection.components.redis.RedisUtil;
import com.jwell.classifiedProtection.entry.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 登录拦截器
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AuthTokenInterceptor implements HandlerInterceptor {

    @Autowired
    RedisUtil redisUtil;

    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("--------------进入拦截器-------------");

        response.setCharacterEncoding("UTF-8");
        String Authorization = request.getHeader("Authorization");
        String path = request.getRequestURI();

        //放行这几个请求（验证码，登录，注册）
        if (path.contains("common")) {
            return true;
        }

        ResultObject resultObject = new ResultObject();
        OutputStream out = response.getOutputStream();
        response.setContentType("application/json; charset=UTF-8");

        try {

            //验证token
            if (StringUtils.isEmpty(Authorization)) {
                resultObject.setCode(ResponseCodeEnum.TOKEN_ERROR.getKey());
                //token缺失即表示用户没有登录
                resultObject.setMsg("token缺失，用户未登录");
                //this.responseMessage(response, response.getWriter(), resultObject);
                out.write(JSON.toJSONString(resultObject).getBytes("UTF-8"));
                return false;

            } else {

                //使用jwt工具中验证方法来验证签名
                Claims claims = JwtUtils.parseJWT(Authorization);
                if (claims != null) {
                    redisUtil.set("Authorization",claims.get("userJsonString", String.class),30*60);//30分钟
                    //将用户信息放入threadLocal中,线程共享
                    User userObject = JSONObject.parseObject(
                            claims.get("userJsonString", String.class), new TypeReference<User>() {});
                    ThreadLocalUtil.getInstance().bind(userObject);
                    return true;
                }
            }
        } catch (ExpiredJwtException e) {

            resultObject.setCode(ResponseCodeEnum.TOKEN_ERROR.getKey());
            resultObject.setMsg("token已过期，请重新登陆！");
            out.write(JSON.toJSONString(resultObject).getBytes("UTF-8"));
            return false;

        } catch (SignatureException | UnsupportedJwtException
                | IllegalArgumentException | MalformedJwtException e) {

            resultObject.setCode(ResponseCodeEnum.TOKEN_ERROR.getKey());
            resultObject.setMsg("token错误，请检查重试！");
            out.write(JSON.toJSONString(resultObject).getBytes("UTF-8"));
            return false;
        }
        return false;

    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) throws Exception {
    }

}

