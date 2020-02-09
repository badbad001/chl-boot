package com.chl.sys.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.sys.common.result.ResultObj;
import com.chl.sys.common.utils.DataGridView;
import com.chl.sys.common.utils.WebUtils;
import com.chl.sys.pojo.Notice;
import com.chl.sys.pojo.User;
import com.chl.sys.service.NoticeService;
import com.chl.sys.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-04
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    /**
     * 查询带条件带分页
     * @param noticeVo
     * @return
     */
    @RequestMapping("queryAllNotice")
    public DataGridView queryAllNotice(NoticeVo noticeVo) {

        IPage<Notice> page  = new Page<>(noticeVo.getPage(), noticeVo.getLimit());
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        //构造条件
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOperName()), "oper_name",noticeVo.getOperName());
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()), "title",noticeVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getContent()), "content", noticeVo.getContent());
        queryWrapper.gt(noticeVo.getStartTime()!=null, "create_time", noticeVo.getStartTime());
        queryWrapper.lt(noticeVo.getEndTime()!=null,"create_time",noticeVo.getEndTime());

        //排序
        queryWrapper.orderByDesc("create_time");

        noticeService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());

    }

    /**
     * 删除
     * @param noticeVo
     * @return
     */
    @RequestMapping("deleteNotice")
    public ResultObj deleteNotice(NoticeVo noticeVo) {
        try {
            noticeService.removeById(noticeVo.getNoticeId());

            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     * @return
     */
    @RequestMapping("deleteBatchNotice")
    public ResultObj deleteBatchNotice(NoticeVo noticeVo) {

        //组装id集合
        Integer[] ids = noticeVo.getIds();
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer id : ids) {
            idList.add(id);
        }

        try {
            noticeService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 添加公告
     * @param noticeVo
     * @return
     */
    @RequiresPermissions("notice:create")   //这个是测试细粒度的shiro
    @RequestMapping("addNotice")
    public ResultObj addNotice(NoticeVo noticeVo) {
        try {

            User user = (User) WebUtils.getHttpSession().getAttribute("user");
            noticeVo.setOperName(user.getUserName());

            noticeService.save(noticeVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 更新公告
     * @param noticeVo
     * @return
     */
    @RequestMapping("updateNotice")
    public ResultObj updateNotice(NoticeVo noticeVo) {
        try {
            noticeService.updateById(noticeVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


}

