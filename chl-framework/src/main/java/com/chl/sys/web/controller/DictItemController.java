package com.chl.sys.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.sys.common.result.ResultObj;
import com.chl.sys.common.utils.DataGridView;
import com.chl.sys.pojo.DictItem;
import com.chl.sys.service.DictItemService;
import com.chl.sys.vo.DictItemVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-07
 */
@RestController
@RequestMapping("dictItem")
public class DictItemController {

    @Autowired
    private DictItemService dictItemService;


    /**
     * 查询带条件带分页
     * @param dictItemVo
     * @return
     */
    @RequestMapping("queryAllDictItem")
    public DataGridView queryAllDictItem(DictItemVo dictItemVo) {

        IPage<DictItem> page  = new Page<>(dictItemVo.getPage(), dictItemVo.getLimit());
        QueryWrapper<DictItem> queryWrapper = new QueryWrapper<>();
        //构造条件
        queryWrapper.lambda().like(
                StringUtils.isNotBlank(dictItemVo.getName()),DictItem::getName,dictItemVo.getName());
        queryWrapper.lambda().like(
                StringUtils.isNotBlank(dictItemVo.getCode()),DictItem::getCode,dictItemVo.getCode());
        queryWrapper.lambda().eq(
                StringUtils.isNotBlank(dictItemVo.getTypeId()),DictItem::getTypeId,dictItemVo.getTypeId());

        //排序
        queryWrapper.lambda().orderByAsc(DictItem::getSort);

        dictItemService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());

    }



    /**
     * 删除
     * @param dictItemVo
     * @return
     */
    @RequestMapping("deleteDictItem")
    public ResultObj deleteDictItem(DictItemVo dictItemVo) {
        try {
            dictItemService.removeById(dictItemVo.getItemId());

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
    @RequestMapping("deleteBatchDictItem")
    public ResultObj deleteBatchDictItem(DictItemVo dictItemVo) {

        //组装id集合
        Integer[] ids = dictItemVo.getIds();
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer id : ids) {
            idList.add(id);
        }

        try {
            dictItemService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 添加字典类型
     * @param dictItemVo
     * @return
     */
    //@RequiresPermissions("dictItem:create")   //这个是测试细粒度的shiro
    @RequestMapping("addDictItem")
    public ResultObj addDictItem(DictItemVo dictItemVo) {
        try {
            dictItemService.save(dictItemVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 更新字典类型
     * @param dictItemVo
     * @return
     */
    @RequestMapping("updateDictItem")
    public ResultObj updateDictItem(DictItemVo dictItemVo) {
        try {
            dictItemService.updateById(dictItemVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

}

