package com.jwell.classifiedProtection.controller.backend;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.commons.utils.JwtUtils;
import com.jwell.classifiedProtection.commons.utils.MD5Utils;
import com.jwell.classifiedProtection.commons.utils.PhoneUtils;
import com.jwell.classifiedProtection.components.redis.RedisUtil;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.User;
import com.jwell.classifiedProtection.entry.dto.UserDto;
import com.jwell.classifiedProtection.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Ronnie
 * @since 2019-08-07
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "用户接口集合", tags = "用户接口集合")
@RestController
@RequestMapping(value = "/backend/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService iUserService;

    @Resource
    private RedisUtil redisUtil;

    @ApiOperation(value = "所有用户列表", notes = "所有用户列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getRectificationAdviceTaskList() {

        ResultObject resultObject = ResultObject.success();
        try {
            List<User> systemList = iUserService.list();
            resultObject.setData(systemList);
        } catch (Exception e) {
            resultObject = ResultObject.failure();
        }

        return resultObject;
    }

    @ApiOperation(value = "用户注册", notes = "用户注册账号")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject register(@RequestBody UserDto userDto) {

        ResultObject resultObject = new ResultObject();

        try {
            User user = userDto.getUser();
            //String passwordconfirm = userDto.getPasswordconfirm();

            if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("用户名或者密码不能为空！");
                return resultObject;
            }

            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.lambda().eq(User::getUserName, user.getUserName());
            User userOne = iUserService.getOne(userQueryWrapper);
            if (userOne != null) {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("用户名已存在");
                return resultObject;
            }

            if (StringUtils.isEmpty(user.getPhone())) {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("电话号码不能为空！");
                return resultObject;
            } else {
                boolean b = PhoneUtils.isPhoneNo(user.getPhone());
                if (!b) {
                    resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                    resultObject.setMsg("电话号码不规范！");
                    return resultObject;
                }
            }

            if (StringUtils.isEmpty(userDto.getCode())) {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("验证码不能为空");
                return resultObject;
            }
            //获取验证码
            Long time = redisUtil.getExpire(user.getPhone());
            if (time == 0) {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("验证码过期！");
                return resultObject;
            } else {
                String verifyCode = (String) redisUtil.get(user.getPhone());
                if (!userDto.getCode().equals(verifyCode)) {
                    resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                    resultObject.setMsg("验证码错误,请检查！");
                    return resultObject;
                }
            }

            user.setPassword(MD5Utils.getMD5(user.getPassword()));
            boolean b = iUserService.saveOrUpdate(user);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }


    @ApiOperation(value = "用户登录", notes = "用户输入账号密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultObject login(HttpServletRequest request, HttpServletResponse response,
                              @RequestBody @Validated UserDto userDto) throws ServletException, IOException {

        ResultObject resultObject = new ResultObject();

        User user = userDto.getUser();
        String code = userDto.getCode();

        if (StringUtils.isEmpty(code)) {
            resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
            resultObject.setMsg("验证码不能为空");
            return resultObject;
        }

        boolean b = redisUtil.hasKey(code);
        if (!b) {
            resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
            resultObject.setMsg("验证码错误！");
            return resultObject;
        }

        Long time = redisUtil.getExpire(code);
        if (time == 0) {
            resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
            resultObject.setMsg("验证码过期！");
            return resultObject;
        }

        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            resultObject.setCode(HttpServletResponse.SC_NOT_FOUND);
            resultObject.setMsg("用户名和账号不能为空");
            return resultObject;
        }

        User userLogin = iUserService.login(user);
        if (userLogin != null) {

            //生成token字符串
            String jwtToken = JwtUtils.createJWT("jwt",
                    "{id:10086,name:Ronnie}", userLogin);

            response.addHeader("Authorization", jwtToken);

            //保存用户到redis,保存时长是30分钟
            redisUtil.set(jwtToken, JSONObject.toJSONString(userLogin), 30 * 60);

            Map map = new HashMap<String, Object>();
            map.put("token", jwtToken);
            map.put("user", userLogin);


            resultObject.setData(map);
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setMsg("登录成功");
            resultObject.setSuccess(Boolean.TRUE);
        } else {
            resultObject.setMsg("登录失败,账号或者密码错误");
            resultObject.setSuccess(Boolean.FALSE);
            resultObject.setCode(HttpServletResponse.SC_NOT_FOUND);
        }
        return resultObject;
    }

    /**
     * 用户分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "用户分页列表", notes = "获取用户分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userName", value = "账号", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "roleType", value = "角色", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "时间", dataType = "LocalDateTime", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject getUserList(String userName, String roleType,
                                    @RequestParam(value = "createTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createTime,
                                    Integer pageNum, Integer pageSize) {

        ResultObject resultObject = new ResultObject();

        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

            if (!StringUtils.isEmpty(userName)) {
                userQueryWrapper.lambda().like(User::getUserName, userName);
            }

            if (!StringUtils.isEmpty(roleType)) {
                userQueryWrapper.lambda().eq(User::getRoleType, roleType);
            }

            if (createTime != null) {
                userQueryWrapper.lambda().eq(User::getCreateTime, createTime);
            }

            IPage<User> userIPage = iUserService.paging(pageNum,pageSize,userQueryWrapper);
            resultObject.setCode(HttpServletResponse.SC_OK);
            resultObject.setData(userIPage);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 新增或者修改用户
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "用户新增或者修改", notes = "用户新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody User user) {

        ResultObject resultObject = new ResultObject();

        try {

            boolean b = iUserService.saveOrUpdate(user);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 用户删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键ID", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject deleteSecurityTemplate(Integer id) {

        ResultObject resultObject = ResultObject.failure();

        try {
            Boolean aBoolean = iUserService.removeById(id);
            resultObject = ResultObject.success();

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询用户详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询用户", notes = "查询用户详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "用户ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Integer id) {

        ResultObject resultObject = ResultObject.failure();
        try {

            User user = iUserService.getDetail(id);
            resultObject = ResultObject.success();
            resultObject.setData(user);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultObject.setMsg("查询失败");
            e.printStackTrace();
        }

        return resultObject;
    }

    @ApiOperation(value = "修改密码", notes = "用户修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    })
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResultObject updatePassword(HttpServletRequest request, HttpServletResponse response,
                              @RequestBody @Validated UserDto userDto){
        return  null;
    }


}