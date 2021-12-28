package com.zimug.courses.security.basic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author AricSun
 * @date 2021.12.28 16:11
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic() // 开启http basic认证
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();  // 所有请求都需要登录认证才能访问
    }
}
