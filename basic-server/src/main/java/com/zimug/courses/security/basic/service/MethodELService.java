package com.zimug.courses.security.basic.service;

import com.zimug.courses.security.basic.model.PersonDemo;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * SpEL权限表达式演示
 * 前提条件，需要在SecurityConfig中开启注解@EnableGlobalMethodSecurity(prePostEnabled = true)
 * @author AricSun
 * @date 2021.12.31 11:31
 */
@Service
public class MethodELService {

    @PreAuthorize("hasRole('admin')")  // 在执行该方法前，使用表达式的内容去判断符合访问权限
    public List<PersonDemo> findAll(){
        return null;
    }

    @PostAuthorize("returnObject.name == authentication.name")  // 在方法执行后判断权限，如果一致则正常返回，不一致则抛出异常
    public PersonDemo findOne(){
        String authName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(authName);
        return new PersonDemo("admin");
    }

    @PreFilter(filterTarget="ids", value="filterObject%2==0")  // 执行前过滤,filterObject表示容器中的元素, 只有符合条件的才能被传入方法中
    public void delete(List<Integer> ids, List<String> usernames){
        System.out.println(ids);
    }

    @PostFilter("filterObject.name == authentication.name")  // 对返回值进行过滤
    public List<PersonDemo> findAllPD(){
        List<PersonDemo> list = new ArrayList<>();
        list.add(new PersonDemo("kobe"));
        list.add(new PersonDemo("admin"));

        return list;
    }
}
