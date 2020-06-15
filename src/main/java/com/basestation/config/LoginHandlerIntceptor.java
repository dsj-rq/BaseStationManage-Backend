package com.basestation.config;

import com.alibaba.fastjson.JSON;
import com.basestation.anootation.LoginRequired;
import com.basestation.bean.CommonResponse;
import com.basestation.bean.User;
import com.basestation.common.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * 拦截所有请求
 * Created by me on 2020/5/4.
 */
@Component
public class LoginHandlerIntceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            Annotation annotation = ((HandlerMethod) handler).getMethodAnnotation(LoginRequired.class);
            if(annotation == null) {
                return true;
            }
            //获取系统当前用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            if(!(principal != null && principal instanceof User)) {
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(new CommonResponse().error(ResultCode.USER_NOT_LOGIN)));
                return false;
            }
         }
        return true;
    }
}
