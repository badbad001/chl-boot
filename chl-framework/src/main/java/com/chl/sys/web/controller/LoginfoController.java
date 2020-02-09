package com.chl.sys.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.sys.common.result.ResultObj;
import com.chl.sys.common.utils.DataGridView;
import com.chl.sys.pojo.Loginfo;
import com.chl.sys.service.LoginfoService;
import com.chl.sys.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-04
 */
@RestController
@RequestMapping("loginfo")
public class LoginfoController {

    @Autowired
    private LoginfoService loginfoService;

    /**
     * 查询日志，带条件加分页
     * @param loginInfoVo
     * @return
     */
    @RequestMapping("queryAllLoginInfo")
    public DataGridView queryAllLoginInfo(LoginfoVo loginInfoVo) {

        IPage<Loginfo> page = new Page<>(loginInfoVo.getPage(),loginInfoVo.getLimit());
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();

        //ip和登录名的
        queryWrapper.like(StringUtils.isNotBlank(loginInfoVo.getLoginName()), "login_name",loginInfoVo.getLoginName());
        queryWrapper.like(StringUtils.isNotBlank(loginInfoVo.getLoginIp()), "login_ip", loginInfoVo.getLoginIp());

        //时间的
        queryWrapper.gt(loginInfoVo.getStartTime()!=null, "login_time", loginInfoVo.getStartTime());
        queryWrapper.lt(loginInfoVo.getEndTime()!=null, "login_time", loginInfoVo.getEndTime());

        //排序
        queryWrapper.orderByDesc("login_time");

        loginfoService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());

    }

    /**
     * 删除日志
     * @return
     */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(LoginfoVo loginInfoVo) {

        try {
            loginfoService.removeById(loginInfoVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 批量删除
     * @param loginInfoVo
     * @return
     */
    @RequestMapping("deleteBatchLoginfo")
    public ResultObj deleteBatchLoginfo(LoginfoVo loginInfoVo) {

        Integer[] ids = loginInfoVo.getIds();
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer id : ids) {
            idList.add(id);
        }

        try {
            loginfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


}

