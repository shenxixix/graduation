package com.yolo.hw.opt.controller;

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
    /**
     * 返回时区对应的毫秒
     * @param request
     * @return
     */
    protected long getZoneLong(HttpServletRequest request) {
        //if(logger.isDebugEnabled()) {
        //    logger.debug(request.getHeader("z-zone"));
        //}
        String offsetStr = request.getHeader("z-zone");
        long offsetInt = 28800;
        if(offsetStr != null && !"".equals(offsetStr.trim())) {
            offsetInt = Long.parseLong(offsetStr);
        }
        return offsetInt;
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
//        return 1328220023103094785L;
//        return 1328163185414307841L;
        //return 1271328614832312322L;
//        return 1272346005255151617l;
//        return 1272489043138883585l;
        return this.getUserId(null, request);
    }

    protected Long getCarId(HttpServletRequest request) {
        return getCarId(null, request);
    }

    protected Long getCarId(Long carIdIn, HttpServletRequest request) {
        Long carId;
        String carIdStr = request.getHeader("z-car");
        if(carIdStr != null && !"".equals(carIdStr.trim())) {
            carId = Long.valueOf(carIdStr);
        } else {
            carId = this.getCookieCarId(carIdIn,request);
        }
        return carId;
    }

    protected Long getCookieCarId(Long carIdIn, HttpServletRequest request) {
        Long carId;
        String carIdStr = this.getCookie(request, "z-car");
        if(carIdStr != null && !"".equals(carIdStr.trim())) {
            carId = Long.valueOf(carIdStr);
        } else {
            carId = carIdIn;
        }
        return carId;
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
    protected String getCookie(HttpServletRequest request,String key){
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
}
