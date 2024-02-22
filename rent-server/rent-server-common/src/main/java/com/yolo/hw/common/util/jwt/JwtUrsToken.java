package com.yolo.hw.common.util.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @Author:shenxi
 * @Des:jwt工具类
 * @Date:2017/11/10
 */
@Log4j2
public class JwtUrsToken {

    //默认密码。用于加密token
    private String key = "jd.car.cn-12345678";
    //签发者
    private final String ISSUER = "yanxiao";

    public JwtUrsToken() {
    }

    public JwtUrsToken(String key) {
        //默认密码
        if (key==null||"".equals(key)){
            return;
        }
        this.key = key;
    }

    /**
     * 用户信息串
     */
    private String subInfo;
    /**
     * 过期时间
     */
    private Date expTime;

    private String token;

    /**
     * 签发时间
     */
    private Date iat;
    /**
     * 接收token标识，可以用于app校验，如包名等,使用者
     */
    private String audience;

    public Date getExpTime() {
        return expTime;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Date getIat() {
        return iat;
    }

    protected String getToken() {
        return token;
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private SecretKey generalKey() {
        String stringKey = key;
        byte[] encodedKey = Base64.getMimeDecoder().decode(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 生成jwt
     *
     * @param subject   主题对象
     * @param ttlMillis 过期时间（秒）
     * @return 返回jwt
     */
    public String genertator(Object subject, Long ttlMillis) {
        if (subject == null) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String val = mapper.writeValueAsString(subject);
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            Date now = new Date();
            long nowMillis = now.getTime();
            SecretKey key = generalKey();
            JwtBuilder builder = Jwts.builder().setIssuer(ISSUER).setAudience(audience).setIssuedAt(now).setSubject(val).signWith(signatureAlgorithm, key);
            if (ttlMillis != null && ttlMillis >= 0) {
                long expMillis = nowMillis + (ttlMillis * 1000);
                Date exp = new Date(expMillis);
                //设置过期时间，暂时不过期
                this.expTime = exp;
                builder.setExpiration(exp);
            }
            this.token = builder.compact();
            this.subInfo = val;
            //此处由于jwt签发时间的bug，时间去掉了毫秒。在业务获取iat时间时毫米部分为000；
            this.iat = new Date((now.getTime() / 1000L) * 1000L);
            return token;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 解密jwt，解析后，可以获取该jwt过期时间、主题json（用于业务解析）、jwt生成时间、接收者标识
     *
     * @param jwt
     */
    public void parseJWT(String jwt) {
        try {
            SecretKey key = generalKey();
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
            this.audience = claims.getAudience();
            this.expTime = claims.getExpiration();
            this.subInfo = claims.getSubject();
            this.iat = claims.getIssuedAt();
            this.token = jwt;
        } catch (ExpiredJwtException e) {
            //过期
            e.printStackTrace();
            throw new RuntimeException("无效用户");
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            throw new RuntimeException("无效用户");
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            throw new RuntimeException("无效用户");
        } catch (SignatureException e) {
            e.printStackTrace();
            throw new RuntimeException("无效用户");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException("无效用户");
        }
    }

    public String getSubInfo() {
        return subInfo;
    }

//    public static void main(String[] args) {
//        String xx="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjb20uc3IubG9naW4iLCJpYXQiOjE1NTY1MTk3MTEsInN1YiI6IntcInVzZXJJZFwiOlwiMVwiLFwib3JnSWRcIjpudWxsLFwiem9uZVwiOm51bGwsXCJwZXJtc1wiOlswLDI2MSwyNjIsMjYzLDI2NCwyNjUsMjY2LDI2NywyNjgsMjY5LDI3MSwyNzIsMjczLDI3NCwyMSwyMywyNCwyODEsMjgyLDMxLDMyLDMzLDI5Niw0MSw0MiwyOTgsNDMsNDQsNDUsMzAxLDQ2LDMwMiw0NywzMDMsNDgsMzA0LDUxLDUyLDUzLDU0LDMxMSw1NSwzMTIsNTYsMzEzLDMxNCwzMTUsNjEsMzIxLDMyMiwzMjMsMzI0LDMyNSw3MSw3Miw3Myw3NCw3NSwzMzEsNzYsNzcsNTkxLDU5Miw4MSw1OTMsODIsMzQxLDkxLDkyLDkzLDM1MSwzNTIsMzUzLDM1NCwzNTUsMzYxLDM2MiwzNjMsMzY0LDM2NSwzNzEsMzcyLDM3MywzNzQsMzgxLDM4MiwzODMsMzg0LDEzMSwxMzIsMTMzLDEzNCwxMzUsMzkxLDM5MiwxMzcsMzkzLDEzOCwzOTQsMTM5LDM5NSwxNDAsMTQxLDM5NywxNDIsMTQzLDM5OSwxNDQsMTQ1LDQwMSwxNDYsMTQ3LDE0OCwxNDksMTUwLDE1MSwxNTIsMTUzLDE1NCwxNTUsMTU2LDE1NywxNTgsMTU5LDE2MCwxNjEsMTYyLDE2MywxNjQsMTY1LDE2NiwxNjcsMTY4LDE2OSwxNzAsMTcxLDE3MiwxNzMsMTc0LDE3NSwxNzYsMTc3LDE3OCwxNzksMTgxLDE4MiwxOTEsMTkzLDE5NCwxOTUsMTk2LDE5NywxOTgsMTk5LDIwMSwyMDIsMjAzLDIwNCwyMDUsMjA2LDIwNywyMTEsMjEyLDIyMSwyMjIsMjIzLDIyNCwyMjUsMjI3LDIzMSw3NTEsMjQxLDI1MV19In0.8YRTyieq4bZ77ZWphRR1clQbv8AKvfflRwvVFPeBivU";
//        //String xx="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhcHAuZ296b29tLmNuIiwiaWF0IjoxNTEwMjk4NDc2LCJzdWIiOiJcIjEyMzEyXCIiLCJleHAiOjE1MTAyOTg0Nzd9.MUfdGFmA3dWoIwjQb1juZSrSxs0oo05EoPuvIRU1J8g";
//        JwtUrsToken jwtUtil = new JwtUrsToken();
//        String sss = "12312";
//        String str = jwtUtil.genertator(sss, 100000l);
//        jwtUtil.parseJWT(xx);
//        System.out.println("==============" + jwtUtil.getSubInfo());
//        System.out.println("==============" + jwtUtil.getAudience());
//        System.out.println("==============" + jwtUtil.getExpTime());
//        System.out.println("==============" + jwtUtil.getIat());
//    }

}


