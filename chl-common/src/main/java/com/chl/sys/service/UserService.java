package com.chl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chl.sys.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-04
 */
public interface UserService extends IService<User> {

    User login(String loginName, String pwd);
}
