package com.chl.sys.service;

import com.chl.sys.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-05
 */
public interface RoleService extends IService<Role> {

    List<Integer> queryRolePermissionId(Integer id);

    void dispatchRolePermission(Integer id, Integer[] ids);

    List<Integer> queryUserRoleIdsByUid(Integer id);

    void addUserRole(Integer id, Integer[] ids);

}
