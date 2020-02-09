package com.chl.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chl.sys.mapper.UserMapper;
import com.chl.sys.pojo.User;
import com.chl.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User getById(Serializable id) {

        return super.getById(id);
    }

    /**
     * 删除用户
     */
    @Override
    public boolean removeById(Serializable id) {
        //删除sys_role_user 中间表的数据
        this.getBaseMapper().deleteUserRoleByUid(id);
        return super.removeById(id);
    }

    @Override
    public boolean save(User entity) {

        return super.save(entity);
    }

    @Override
    public boolean updateById(User entity) {

        return super.updateById(entity);
    }

    @Override
    public User login(String loginName, String pwd) {

       return userMapper.login(loginName,pwd);

    }
}
