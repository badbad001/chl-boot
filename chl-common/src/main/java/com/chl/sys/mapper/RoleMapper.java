package com.chl.sys.mapper;

import com.chl.sys.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-05
 */
public interface RoleMapper extends BaseMapper<Role> {


    //删除sys_role_permission中间表的数据
    void deleteRolePermissionByRid(Serializable id);

    //删除sys_role_user中间表的数据
    void deleteRoleUserByRid(Serializable id);

    //根据角色id去中间表查 角色所拥有的权限id集合
    List<Integer> queryRolePermissionId(Integer id);

    //添加角色权限
    void addRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);

    //查询用户角色id集合  根据用户id
    List<Integer> queryUserRoleIdsByUid(Integer id);

    //删除用户角色
    void deleteUserRoleByUid(Integer uid);

    //添加用户角色
    void addUserRole(@Param("rid") Integer rid, @Param("uid") Integer uid);
}
