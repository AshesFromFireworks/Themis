package com.themis.MyMvcConfig;

import com.themis.Model.Result;
import com.themis.Utils.Jwt;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        try{
            Jwt.verJWT(request.getHeader("token"));
            return true;
        } catch (Exception e){
            response.setStatus(401);
            return false;
        }

    }
}
