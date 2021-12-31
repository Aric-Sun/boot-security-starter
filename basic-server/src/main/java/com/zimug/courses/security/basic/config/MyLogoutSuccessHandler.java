package com.zimug.courses.security.basic.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author AricSun
 * @date 2021.12.31 16:45
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication)
            throws IOException, ServletException {
        /* 退出相关发自定义逻辑 */
        httpServletResponse.sendRedirect("/login.html");
    }
}
