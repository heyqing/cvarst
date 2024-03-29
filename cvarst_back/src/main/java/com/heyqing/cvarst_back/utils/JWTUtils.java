package com.heyqing.cvarst_back.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:JWTUtils
 * Package:com.heyqing.cvarst_back.utils
 * Description:
 *
 * @Date:2023/11/23
 * @Author:Heyqing
 */

public class JWTUtils {
    private static final String jwtToken = "123456heyqing!@#$$";

    public static String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000));// 过期时间 12小时的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token) {
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
