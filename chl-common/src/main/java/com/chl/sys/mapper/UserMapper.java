package com.chl.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chl.sys.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-04
 */
public interface UserMapper extends BaseMapper<User> {

    User login(@Param("loginName") String loginName, @Param("pwd") String pwd);


    List<User> findAll();

    //删除sys_role_user 中间表的数据
    void deleteUserRoleByUid(Serializable id);
}
