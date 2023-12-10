package com.kirito.management.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.Calendar;
import java.util.Map;


public class JWTUtils {

    /**
     * 生成token  header.payload.signature
     */
    private static final String sign = "Polaris"; //设置签名

    public static String generateToken(Map<String, Object> map) {
        Calendar instance = Calendar.getInstance();
        //设置Token过期时间
        //The days of the week are represented as integers,
        // where 1 represents Sunday, 2 represents Monday, and so on, up to 7 which represents Saturday
        instance.add(Calendar.HOUR, 1);
        //创建JWTBuilder
        JWTCreator.Builder builder = JWT.create();
        //获取Token的payload载荷部分
        //map.forEach(builder::withClaim);
        map.forEach((k, v) -> builder.withClaim(k, String.valueOf(v)));

        String Token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(sign));
        return Token;
    }

    public static DecodedJWT verifyToken(String token) {
        //System.out.println("JWTverify      "+token);
        return JWT.require(Algorithm.HMAC256(sign)).build().verify(token);
    }

}
