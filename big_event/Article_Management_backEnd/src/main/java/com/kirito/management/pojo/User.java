package com.kirito.management.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Accessors(chain = true) //开始链式调用
public class User {
    //id
    private Integer id;
    //用户名
    @Pattern(regexp = "[a-z0-9A-Z_]{3,15}")
    private String username;
    //昵称
    //5-15个字符,非空
    @Pattern(regexp = "^.{4,15}$")
    private String nickname;
    // 头像
    @URL
    private String avatar;
    //邮箱
    @Email(message = "请输入正确的邮箱")
    private String email;
    //密码
    @JsonIgnore  //转JSON的时候自动忽略
    //密码至少六位,至少包含一个特殊字符,以及大小写
    @Pattern(regexp = "^\\S*(?=\\S{6,})(?=\\S*\\d)(?=\\S*[a-z])(?=\\S*[!@#$%^&*?.])\\S*$")
    private String password;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    // 最后登录时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

}
