package com.yolo.hw.user.controller;

import com.yolo.hw.common.util.ZMDateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractController {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    protected String getZone(HttpServletRequest request) {
        String offsetStr = request.getHeader("z-zone");
        int offsetInt = 28800;
        if(offsetStr != null && !"".equals(offsetStr.trim())) {
            offsetInt = Integer.parseInt(offsetStr);
        }
        return ZMDateTimeUtil.getZoneId(offsetInt);
    }

    protected Long getUserId(Long userIdIn, HttpServletRequest request) {
        Long userId;
        String userIdStr = request.getHeader("userId");
        if(userIdStr != null && !"".equals(userIdStr.trim())) {
            userId = Long.valueOf(userIdStr);
        } else {
            userId = this.getCookieUserId(userIdIn,request);
        }
        return userId;
    }
    protected Long getUserId(HttpServletRequest request) {
        return this.getUserId(null, request);
    }

    protected Long getCookieUserId(Long userIdIn, HttpServletRequest request) {
        Long userId;
        String userIdStr = this.getCookie(request, "userId");
        if(userIdStr != null && !"".equals(userIdStr.trim())) {
            userId = Long.valueOf(userIdStr);
        } else {
            userId = userIdIn;
        }
        return userId;
    }

    /**
     * String app = request.getHeader("z-app");
       String osType = request.getHeader("z-sys");
       String lng = request.getHeader("z-lng");
       String lat = request.getHeader("z-lat");
       String zone = request.getHeader("z-zone");
       String zver = request.getHeader("z-ver");
       String zcar = request.getHeader("z-car");
       String userId = request.getHeader("userId");
     * @param request
     * @param key
     * @return
     */
    protected String getCookie(HttpServletRequest request, String key){
        String val = null;
        val=request.getHeader(key);
        if(val!=null){
            return val;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equalsIgnoreCase(cookie.getName())) {
                    val = cookie.getValue();
                    //logger.warn("获取cookie值>>name:" + cookie.getName() + ",value:" + cookie.getValue());
                    break;
                }
            }
        }
        return val;
    }

    /**
     * String app = request.getHeader("z-app");
     String osType = request.getHeader("z-sys");
     String lng = request.getHeader("z-lng");
     String lat = request.getHeader("z-lat");
     String zone = request.getHeader("z-zone");
     String zver = request.getHeader("z-ver");
     String zcar = request.getHeader("z-car");
     String userId = request.getHeader("userId");
     * @param request
     * @param key
     * @return
     */
    protected String getHeaderBeforCookie(HttpServletRequest request,String key){
        String val = null;
        val=request.getHeader(key);
        if(val!=null){
            return val;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equalsIgnoreCase(cookie.getName())) {
                    val = cookie.getValue();
                    break;
                }
            }
        }
        return val;
    }
}
