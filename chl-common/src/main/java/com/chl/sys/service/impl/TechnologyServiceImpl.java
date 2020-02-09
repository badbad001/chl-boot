package com.chl.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.chl.sys.pojo.Technology;
import com.chl.sys.mapper.TechnologyMapper;
import com.chl.sys.service.TechnologyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-05
 */
@Service
public class TechnologyServiceImpl extends ServiceImpl<TechnologyMapper, Technology> implements TechnologyService {


    //查询用户技术的id集合
    @Override
    public List<Integer> queryUserTechnologyByUid(Integer id) {
        return this.getBaseMapper().queryUserTechnologyByUid(id);
    }


    //分配用户技术点
    @Override
    public void dispatchUserTechnology(Integer id, List<Integer> tids) {
        //先删除中间表的数据，避免主键重复问题
        TechnologyMapper technologyMapper = this.getBaseMapper();

        //删除删除中间表的数据，避免主键重复问题
        technologyMapper.deleteUserTechnologyByUid(id);

        //不为空插入数据库
        if (CollectionUtil.isNotEmpty(tids)){
            //分配用户技术
            for (Integer tid : tids) {
                technologyMapper.addUserTechnology(id,tid);
            }
        }

    }

}
