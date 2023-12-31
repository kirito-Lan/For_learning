package com.kirito.management.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kirito.management.pojo.Result;
import com.kirito.management.pojo.User;
import com.kirito.management.service.UserService;
import com.kirito.management.utils.JWTUtils;
import com.kirito.management.utils.MD5Utils;
import com.kirito.management.utils.ThreadLocalUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //注册功能
    @PostMapping("/register")
    public Result<String> Register(@Validated User user, BindingResult res) {
        //System.out.println(user);
        User u = userService.getUserByUserName(user.getUsername());
        if (u != null) {
            return Result.fail("用户已存在");
        } else if (res.hasErrors()) {
            return Result.fail("账号或密码,不符合要求请修改");
        }
        return userService.register(user) ? Result.success("注册成功") : Result.fail("注册失败");
    }

    //登录
    @PostMapping("/login")
    public Result<String> Login(@RequestParam String username, @RequestParam String password) {
        User user = userService.getUserByUserName(username);
        //System.out.println(user);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        if (!user.getPassword().equals(MD5Utils.encrypt(password))) {
            return Result.fail("密码错误");
        }
        //更新登录时间
        userService.updateLoginTime(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = JWTUtils.generateToken(claims);
        //将token存放到Redis中
        ValueOperations<String, String> value = stringRedisTemplate.opsForValue();
        value.set(token, token, 1, TimeUnit.HOURS);
        return Result.success(token);
    }

    //获取用户信息
    @GetMapping("/getUserInfo")
    public Result<User> getUserInfo() {
        //从拦截器中解析的token获取信息
        DecodedJWT decodeJwt = ThreadLocalUtils.get();
        Map<String, Claim> claims = decodeJwt.getClaims();
        User user = userService.getUserByUserName(claims.get("username").asString());
        return Result.success(user);
    }

    //设置头像
    @PatchMapping("/setAvatar")
    public Result<String> setAvatar(@Validated @RequestParam String imgUrl) {
        DecodedJWT decodeJwt = ThreadLocalUtils.get();
        Map<String, Claim> claims = decodeJwt.getClaims();
        return userService.setAvatar(claims.get("id").asString(), imgUrl) ?
                Result.success()
                : Result.fail("设置失败,请重试");
    }

    //更新用户信息
    @PutMapping("/updateInfo")
    public Result<String> updateInfo(@Validated @RequestBody User user) {
        return userService.updateUserInfo(user) ? Result.success() : Result.fail("更新失败");
    }

    //修改密码
    @PatchMapping("/modifyPwd")
    public Result<String> modifyPwd(@Validated @RequestBody Map<String, String> pwd) {
        DecodedJWT decodeJwt = ThreadLocalUtils.get();
        Map<String, Claim> claims = decodeJwt.getClaims();
        User user = userService.getUserByUserName(claims.get("username").asString());
        boolean equals = user.getPassword().equals(MD5Utils.encrypt(pwd.get("old_pwd")));
        if (!equals) {
            return Result.fail("原密码错误!");
        } else if (!pwd.get("new_pwd").equals(pwd.get("re_pwd"))) {
            return Result.fail("两次密码需要一致");
        }
        ValueOperations<String, String> value = stringRedisTemplate.opsForValue();
        value.getOperations().delete(decodeJwt.getToken());
        return userService.modifyPwd(claims.get("username").asString(), pwd.get("new_pwd"))
                ? Result.success()
                : Result.fail("修改失败,请重试!");
    }


}
