package com.zimug.courses.security.basic.mapper;

import com.zimug.courses.security.basic.model.MyUserDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author AricSun
 * @date 2021.12.30 14:29
 */
public interface MyUserDetailsMapper {

    // 根据userId查询用户信息
    @Select("select username,password,enabled " +
            "from sys_user u " +
            "where u.username = #{userId} ")
    MyUserDetails findByUsername(@Param("userId") String userId);

    // 根据userId查询用户所具有的角色列表
    @Select("select role_code " +
            "from sys_role r " +
            "left join sys_user_role ur on r.id = ur.role_id " +
            "left join sys_user u on u.id=ur.user_id " +
            "where u.username = #{userId} ")
    List<String> findRoleByUsername(@Param("userId") String userId);

    // 根据用户角色查询用户权限（菜单（请求路径））
    @Select("<script>\n" +
            "\tselect url\n" +
            "\tfrom sys_menu m\n" +
            "\t\tleft join sys_role_menu rm on m.id = rm.menu_id\n" +
            "\t\tleft join sys_role r on r.id = rm.role_id\n" +
            "\twhere r.role_code in \n" +
            "\t\t<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'>\n" +
            "\t\t\t#{roleCode}\n" +
            "\t\t</foreach>\n" +
            "</script>")
    List<String> findAuthorityByRoleCodes(@Param("roleCodes") List<String> roleCodes);
}
