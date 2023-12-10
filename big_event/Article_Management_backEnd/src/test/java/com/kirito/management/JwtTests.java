package com.kirito.management;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kirito.management.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class JwtTests {

    @Test
    public String generateJwt() {
        HashMap<String, Object> map = new HashMap<>();
        // 获取表示当前时间的Calendar实例
        Calendar instance = Calendar.getInstance();
        // 在当前时间上加10秒
        instance.add(Calendar.SECOND, 3);

        String Token = JWT.create()
                .withHeader(map)
                .withClaim("username", "kirito") //payload 载荷部分
                .withClaim("userid", 66) //payload 载荷部分
                .withExpiresAt(instance.getTime())  //过期时间
                .sign(Algorithm.HMAC256("Asuna"));//签名加密
        System.out.println(Token);
        return Token;
    }

    @Test
    public void verifyJwt() {
        String token = generateJwt();
        //获取一个解密的对象,填入秘钥
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("Asuna")).build();
        try {
            Thread.sleep(2000); // 暂停2秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        System.out.println(decodedJWT.getPayload());
        System.out.println(decodedJWT.getClaim("username"));
        System.out.println("过期时间:  " + decodedJWT.getExpiresAt());
    }


    @Test
    public void test() {

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "zhangsan");
        claims.put("id", 1);

        String token = JWTUtils.generateToken(claims);

        DecodedJWT decodedJWT = JWTUtils.verifyToken(token);
        Map<String, Claim> claims1 = decodedJWT.getClaims();
        for (String key : claims1.keySet()) {
            //System.out.println(claims1.get(key).getClass());
            System.out.println(key + ":" + claims1.get(key));
        }
        System.out.println(claims1.get("id").asString());

        /*
        * 当使用JWT(JSON Web Token)解析一个token的时候,decodedJWT.getClaims()返回的是一个Map,key是claim的名字,value是claim的值对象。
           这里返回Claim类型而不是直接返回字符串或其他类型的原因主要有:
           1. Claim值对象可以携带不同类型的数据,不仅仅是String。比如一个claim的值可以是一个JSON对象。使用Claim类型更灵活。
           2. Claim支持不同的claim类型,比如ReservedClaims、PublicClaims等,可以表示不同的语义。
           3. Claim值对象包含了一些元数据,比如是否是null值等标志。
           所以为了表达能力更强,避免类型casting,decodedJWT返回Claim类型。
           你不能直接把Claim转为String,正确的方式是调用Claim的asString()方法来获取其中的字符串值:
           ```
           String strValue = claims.get("name").asString();
           ```
           所以使用Claim更类型安全,也能存储更多样的数据,这就是采用Claim类型的好处。
        * */
    }

}
