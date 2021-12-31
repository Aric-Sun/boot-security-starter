package com.zimug.courses.security.basic.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AricSun
 * @date 2021.12.30 17:08
 */
@Component("rbacService")
public class MyRBACService {
    // 方法名字随意
    public boolean hasPermission(HttpServletRequest request, Authentication authentication){
        Object principal = authentication.getPrincipal();  // 这个实际上就是登录认证的主体信息

        if (principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;

            // 将请求路径作为唯一参数构造一个simpled的授权的访问资格，
            // 因为UserDetails中的Authority内部是GrantedAuthority类型的，Simple~是其中一个实现类
            // 意为*本次*要访问的资源
            SimpleGrantedAuthority simpleGrantedAuthority =
                    new SimpleGrantedAuthority(request.getRequestURI());
            // 这个userDetails，也就是authentication，是登录的时候就生成的，
            // 所以此操作是获取该用户的所有权限名称,并判断是否有权限访问request中的资源
            return userDetails.getAuthorities().contains(simpleGrantedAuthority);
        }

        return false;
    }
}