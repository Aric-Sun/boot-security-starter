package com.zimug.courses.security.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author AricSun
 * @date 2021.12.28 16:11
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  // 暂时关闭跨站攻击防御
            .formLogin() // 开启formLogin
                .loginPage("/login.html")  // 一旦用户的请求没有权限就跳转到这个页面
                .loginProcessingUrl("/login")  // 表示登录表格的action指向
                .usernameParameter("username")
                .passwordParameter("password")  // 这两个是表单的输入框的名字name
//                .defaultSuccessUrl("/")  // 登录成功后去往何处
//                .failureUrl("/login.html")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
            .and()
                .authorizeRequests()
                    .antMatchers("/login.html", "/login").permitAll()  // 不需要通过验证就能访问
                    .antMatchers("/", "/biz1", "/biz2")  // 资源路径匹配
                        .hasAnyAuthority("ROLE_user", "ROLE_admin")  // 有权限就可以访问
    //                .antMatchers("/syslog", "/sysuser")
    //                    .hasRole("admin")  // 有角色就可以访问
                    .antMatchers("/syslog").hasAuthority("sys:log")
                    .antMatchers("/sysuser").hasAuthority("sys:user")  // 对应下面authorities
                // 觉得是一种特殊的权限，等价，Role_是固定前缀，连起来表示id，hasRole里面的名字表示名字，ROLE_admin == admin
                .anyRequest()  // 任何请求
                .authenticated()  // 请求需要登录认证才能访问
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // IF_REQUIRED表示需要再生成，
                                                                        // STATELESS表示无状态的，也就是前后端分离
//                    .invalidSessionUrl("/invalidSession.html")  // 当session到期后跳转的页面
                    .sessionFixation().migrateSession()  // session保护：默认在每一次登录后更换一个新的sessionID并且迁移旧session的属性
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("user")
            .and()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
//                .roles("admin")  // 对应上面hasRole
                .authorities("sys:log", "sys:user")  // 对应上面hasAuthority
            .and()
                .passwordEncoder(passwordEncoder());  // 因为调用了bcrypt的加密，所以要告诉他怎么解密，会自动调用matches
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 将项目中静态资源路径开放出来
        web.ignoring().antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**");
        // ignoring 表示所有指定的路径都不会经过过滤器，直接被放行
    }
}
