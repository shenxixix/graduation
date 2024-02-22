package com.yolo.config.weblog;

import com.yolo.validator.StValiDator;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用户controller的requestbody验证和请求日志打印
 */
@Log4j2
@Aspect
@Component
public class WebLogInterceptor {
    //系统换行符，用于日志打印
    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 定义拦截规则：拦截controller包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* *.*(..)) && (@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PatchMapping)" +
            ")")
    protected void controllerMethodPointcut() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("controllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        //参数
        Object[] args = pjp.getArgs();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();

        Class<?> target = method.getReturnType();

        Annotation[][] paramAnnotation= method.getParameterAnnotations();
        Set<Object> parms = new HashSet<>();
        //请求地址
        String path = "";
        HttpServletResponse response = null;
        Map<String, Object> validatorMap = null;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest) {
                    HttpServletRequest request = (HttpServletRequest) args[i];
                    path = request.getRequestURI();
                } else if (args[i] instanceof HttpServletResponse) {
                    response = (HttpServletResponse) args[i];
                } else {
                    parms.add(args[i]);
                    //这里可以做个验证
                    if (paramAnnotation!=null&&paramAnnotation.length>0){
                        for (Annotation a:paramAnnotation[i]){
                            if (a.annotationType()== RequestBody.class){
                                validatorMap = StValiDator.validate(args[i]);
                            }
                        }
                    }
                }
            }
        }
        if (path == null || "".equals(path)) {
            path = this.getPath(pjp);
        }
        Object result = "----";
        long beginTime = System.currentTimeMillis();
        try {
            if (validatorMap != null) {
                if (response != null) {
                    response.setStatus(602);
                }
                result="--参数验证未通过--";

//                if (target == Result.class) {
//                    return Result.failed(validatorMap);
//                }

                return validatorMap;
            }
            //执行方法
            result = pjp.proceed();
            return result;
        } catch (Throwable e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long ms = endTime - beginTime;
            StringBuilder sb = new StringBuilder();
            sb.append(LINE_SEPARATOR).append("********************************************************************************************");
            sb.append(LINE_SEPARATOR).append("请求路径>>").append(path);
            sb.append(LINE_SEPARATOR).append("请求方法>>").append(methodName);
            sb.append(LINE_SEPARATOR).append("参数>>").append(parms);
            sb.append(LINE_SEPARATOR).append("耗时>>").append(ms).append("毫秒");
            sb.append(LINE_SEPARATOR).append("结果>>").append(result);
            sb.append(LINE_SEPARATOR).append("============================================================================================");
            log.info(sb.toString());
        }
    }

    private String getPath(ProceedingJoinPoint pjp) {

        Class doClass = pjp.getSignature().getDeclaringType();
        Object classReq = doClass.getDeclaredAnnotation(RequestMapping.class);
        //获取请求地址路径，有时路径写在class上的，需要与方法的拼接
        String path1 = "";
        if (classReq != null && classReq instanceof RequestMapping) {
            String val[] = ((RequestMapping) classReq).value();
            path1 = val[0];
        }
        // 获取方法上的路径
        String method = pjp.getSignature().getName();
        Method[] methods = doClass.getMethods();
        String path2 = "";
        for (Method m : methods) {
            if (method.equals(m.getName())) {
                RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
                if (requestMapping != null) {
                    if (requestMapping.value() != null && requestMapping.value().length > 0) {
                        path2 = requestMapping.value()[0];
                    }
                }
                break;
            }
        }
        return path1 + path2;
    }
}
