package com.chl.sys.service;

import com.chl.sys.pojo.Technology;
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
public interface TechnologyService extends IService<Technology> {


    List<Integer> queryUserTechnologyByUid(Integer id);

    void dispatchUserTechnology(Integer id, List<Integer> tids);
}
