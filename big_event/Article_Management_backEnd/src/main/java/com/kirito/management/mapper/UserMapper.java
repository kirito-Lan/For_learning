package com.kirito.management.mapper;


import com.kirito.management.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {


    //通过用户名查询用户
    @Select("select * from big_event.user where username=#{username}")
    User getUserByUserName(String username);

    // 更新用户信息
    @Update("update big_event.user set nickname=#{nickname},email=#{email} where id=#{id}")
    int updateUserInfo(User user);

    //注册时
    @Insert("insert into big_event.user ( username,  password, create_time, last_login_time) VALUES (#{username},#{password},now(),now())")
    int Register(User user);

    //更新登录时间
    @Update("update big_event.user set last_login_time=now() where username=#{username}")
    int updateLoginTime(String username);

    //更新用户头像
    @Update("update big_event.user set avatar=#{imgUrl} where id=#{id}")
    int setAvatar(String id, String imgUrl);

    //修改密码
    @Update("update big_event.user set password=#{new_pwd} where username=#{username}")
    int modifyPwd(String new_pwd, String username);


}
