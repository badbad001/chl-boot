package com.chl.sys.mapper;

import com.chl.sys.pojo.Technology;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-05
 */
public interface TechnologyMapper extends BaseMapper<Technology> {

    //查询用户技术的id集合
    List<Integer> queryUserTechnologyByUid(Integer id);

    //删除用户技术中间表数据
    void deleteUserTechnologyByUid(Integer id);

    //分配用户技术
    void addUserTechnology(@Param("uid") Integer uid, @Param("tid") Integer tid);
}
