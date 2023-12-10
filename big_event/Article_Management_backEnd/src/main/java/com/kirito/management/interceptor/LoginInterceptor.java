package com.kirito.management.interceptor;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kirito.management.pojo.Result;
import com.kirito.management.utils.JWTUtils;
import com.kirito.management.utils.ThreadLocalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取到Token
        String token = request.getHeader("Authorization");
        //验证token
        try {
            //System.out.println("loginInterceptor         "+token);
            ValueOperations<String, String> value = stringRedisTemplate.opsForValue();
            String RedisToken = value.get(token);
            if (RedisToken==null){
                //表示token过期或者不存在
                throw new RuntimeException();
            }
            DecodedJWT decodedJWT = JWTUtils.verifyToken(token);
            ThreadLocalUtils.set(decodedJWT);
            //放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(Result.fail("Please Login before Search")));
            //不放行
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.remove();
    }
}
