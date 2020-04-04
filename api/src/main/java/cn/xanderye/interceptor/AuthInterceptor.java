package cn.xanderye.interceptor;

import cn.xanderye.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Value("${password}")
    private String password;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String auth = request.getHeader("auth");
        if (!password.equals(auth)) {
            throw new BusinessException("授权码错误");
        }
        return true;
    }

}