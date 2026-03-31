package com.zjy.Utils;

import com.zjy.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private static final String SECRET_KEY = "mySecretKeyZjyAsuka1234567890123";
    // 过期时间：2小时
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 2;

    private static SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public static String createToken(User user) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("number",user.getNumber());
        claims.put("dorm",user.getDorm());
        return Jwts.builder()
                .setClaims(claims)  // 存放用户信息
                .setIssuedAt(new Date())  // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 过期时间
                .signWith(getKey())  // 签名
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Map<String,Object> getUserMap(String token) {
        Map<String,Object> result = new HashMap<>();
        result.put("number",parseToken(token).get("number", String.class));
        result.put("dorm",parseToken(token).get("dorm", String.class));
        return result;
    }

    public static String getTokenByHeader(String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new RuntimeException("token错误");
        }
        return authHeader.substring(7);
    }
}
