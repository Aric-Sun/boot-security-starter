package com.zimug.courses.security.basic.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AricSun
 * @date 2021.12.29 16:16
 */
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    // 页面跳转的处理逻辑
//    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    // JSON processor from Jackson
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        // 页面跳转的方式
//        redirectStrategy.sendRedirect(
//                sessionInformationExpiredEvent.getRequest(),
//                sessionInformationExpiredEvent.getResponse(),
//                ""
//        );
        Map<String, Object> map = new HashMap<>();
        map.put("code", 403);
        map.put("msg", "您的登录已经超时或者已经在另一台机器登录，您被迫下线。"
                + sessionInformationExpiredEvent.getSessionInformation().getLastRequest());
        String json = objectMapper.writeValueAsString(map);
        sessionInformationExpiredEvent.getResponse().setContentType("application/json;charset=UTF-8");
        sessionInformationExpiredEvent.getResponse().getWriter().write(json);
    }
}
