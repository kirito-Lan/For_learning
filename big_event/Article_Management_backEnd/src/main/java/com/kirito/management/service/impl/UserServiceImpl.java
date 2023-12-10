package com.kirito.management.service.impl;

import com.kirito.management.mapper.UserMapper;
import com.kirito.management.pojo.User;
import com.kirito.management.service.UserService;
import com.kirito.management.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    //通过用户名获取User
    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    @Override
    public boolean register(User user) {
        // 密码加密
        String encrypted = MD5Utils.encrypt(user.getPassword());
        user.setPassword(encrypted);
        return userMapper.Register(user)!=0;
    }

    // 更新用户登录时间
    @Override
    public boolean updateLoginTime(String username) {
        return userMapper.updateLoginTime(username)!=0;
    }

    //设置头像
    @Override
    public boolean setAvatar(String id, String imgUrl) {
        return userMapper.setAvatar(id, imgUrl)!=0;
    }

    //修改密码
    @Override
    public boolean modifyPwd(String username, String new_pwd) {
        return userMapper.modifyPwd(MD5Utils.encrypt(new_pwd),username)!=0;
    }

    //更新用户信息
    @Override
    public boolean updateUserInfo(User user) {
        return userMapper.updateUserInfo(user)!= 0;

    }


}
