package com.kirito.management.service;

import com.kirito.management.pojo.User;


public interface UserService {
    User getUserByUserName(String username);

    boolean register(User user);

    boolean updateLoginTime(String username);


    boolean updateUserInfo(User user);


    boolean setAvatar(String id,String img);

    boolean modifyPwd(String username,String new_pwd);
}
