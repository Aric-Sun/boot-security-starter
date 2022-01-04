package com.zimug.courses.security.basic.service;

import com.zimug.courses.security.basic.mapper.MyUserDetailsMapper;
import com.zimug.courses.security.basic.model.MyUserDetails;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AricSun
 * @date 2021.12.30 14:02
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    MyUserDetailsMapper myUserDetailsMapper;
    /*
     * function:
     * @Param [s]: SecurityConfig类中usernameParameter("username")的值，表示唯一标识，不一定就是用户名
     * @Return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 用户基础数据加载
        MyUserDetails myUserDetails = myUserDetailsMapper.findByUsername(s);
        if (myUserDetails == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 用户的角色列表
        List<String> roleCodes = myUserDetailsMapper.findRoleByUsername(s);

        // 根据角色列表加载当前用户具有的权限
        List<String> authorities = myUserDetailsMapper.findAuthorityByRoleCodes(roleCodes);

        // 角色是一种特殊的权限，而角色权限的前缀是ROLE_，所以要在角色名字前加上ROLE_，一并加入权限列表
        roleCodes = roleCodes.stream()
                .map(rc -> "ROLE_" + rc)
                .collect(Collectors.toList());
        // 一并加入权限列表
        authorities.addAll(roleCodes);
        // 将权限列表设置为用户对象的属性
        myUserDetails.setAuthorities(
                // 官方工具类，从逗号分隔的字符串表示形式创建一个 GrantedAuthority 对象数组。
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                        // 以逗号为分界，将序列的所有元素拼成一个字符串
                        // 第二个参数需要实现Iterable接口，元素为字符串
                        String.join(",", authorities)
        ));

        return myUserDetails;
    }
}
