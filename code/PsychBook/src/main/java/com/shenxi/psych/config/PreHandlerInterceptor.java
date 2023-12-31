package com.shenxi.psych.config;

import com.shenxi.psych.entity.Admin;
import com.shenxi.psych.entity.Doctor;
import com.shenxi.psych.entity.Patient;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:拦截器
 */
public class PreHandlerInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
            //判断是否是咨询者
            Patient patient = (Patient) request.getSession().getAttribute("patient");
            //判断是否是心理医生
            Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");
            //判断是否是管理员
            Admin admin = (Admin) request.getSession().getAttribute("admin");

//            String user=(String) request.getSession().getAttribute("userid");
            if(patient != null || doctor != null || admin != null){
                return true;
            }
            //重定向到咨询者登录页面
            response.sendRedirect(request.getContextPath()+"/stu");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;//如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//         System.out.println("执行了TestInterceptor的postHandle方法");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        System.out.println("执行了TestInterceptor的afterCompletion方法");
    }
}
