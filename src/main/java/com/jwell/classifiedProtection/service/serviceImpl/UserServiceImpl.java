package com.jwell.classifiedProtection.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwell.classifiedProtection.entry.User;
import com.jwell.classifiedProtection.mapper.UserMapper;
import com.jwell.classifiedProtection.service.IUserService;
import com.jwell.classifiedProtection.commons.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统 服务实现类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int regster(User user) {

        user.setPassword(MD5Utils.getMD5(user.getPassword()));
        int b = userMapper.insert(user);

        return b;
    }

    @Override
    public User login(User user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserName, user.getUserName())
                .and(e -> e.eq(User::getPassword, MD5Utils.getMD5(user.getPassword())));

        User userLogin = userMapper.selectOne(queryWrapper);
        return userLogin;
    }

    @Override
    public IPage<User> paging(Integer pageNum,Integer pageSize,
                              QueryWrapper<User> queryWrapper) {

        queryWrapper.lambda().orderByDesc(User::getCreateTime);

        IPage iPage = new Page(pageNum, pageSize);
        IPage<User> userIPage = userMapper.selectPage(iPage, queryWrapper);
        return userIPage;
    }

    @Override
    public User getDetail(Integer id) {

        User user = userMapper.selectById(id);
        return user;
    }
}
