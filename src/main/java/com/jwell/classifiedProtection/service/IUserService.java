package com.jwell.classifiedProtection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwell.classifiedProtection.entry.User;

/**
 * <p>
 * 系统 服务类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-02
 */
public interface IUserService extends IService<User> {

    /**
     * 用户注册
     */
    int regster(User user);

    /**
     * 用户登录
     */
    User login(User user);

    /**
     * 用户分页列表
     * @param queryWrapper
     * @return
     */
    IPage<User> paging(Integer pageNum,Integer pageSize,QueryWrapper<User> queryWrapper);


    /**
     * 根据用户ID查询信息详情
     */
    User getDetail(Integer id);

}
