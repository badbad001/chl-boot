package com.chl.sys.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.sys.common.result.ResultObj;
import com.chl.sys.common.utils.DataGridView;
import com.chl.sys.common.utils.WebUtils;
import com.chl.sys.pojo.DictItem;
import com.chl.sys.pojo.DictType;
import com.chl.sys.pojo.User;
import com.chl.sys.service.DictTypeService;
import com.chl.sys.vo.DictTypeVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("dictType")
public class DictTypeController {


    @Autowired
    private DictTypeService dictTypeService;


    /**
     * 查询带条件带分页
     * @param dictTypeVo
     * @return
     */
    @RequestMapping("queryAllDictType")
    public DataGridView queryAllDictType(DictTypeVo dictTypeVo) {

        IPage<DictType> page  = new Page<>(dictTypeVo.getPage(), dictTypeVo.getLimit());
        QueryWrapper<DictType> queryWrapper = new QueryWrapper<>();
        //构造条件
        queryWrapper.lambda().like(
                StringUtils.isNotBlank(dictTypeVo.getName()),DictType::getName,dictTypeVo.getName());
        queryWrapper.lambda().like(
                StringUtils.isNotBlank(dictTypeVo.getCode()),DictType::getCode,dictTypeVo.getCode());

        //排序
        queryWrapper.lambda().orderByAsc(DictType::getSort);

        dictTypeService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());

    }

    /**
     * 查询所有类型
     * @return
     */
    @RequestMapping("queryAllDictForList")
    public DataGridView queryAllDictForList(){
        List<DictType> list = dictTypeService.list();
        return new DataGridView(list);
    }

    /**
     * 删除
     * @param dictTypeVo
     * @return
     */
    @RequestMapping("deleteDictType")
    public ResultObj deleteDictType(DictTypeVo dictTypeVo) {
        try {
            dictTypeService.removeById(dictTypeVo.getTypeId());

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
    @RequestMapping("deleteBatchDictType")
    public ResultObj deleteBatchDictType(DictTypeVo dictTypeVo) {

        //组装id集合
        Integer[] ids = dictTypeVo.getIds();
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer id : ids) {
            idList.add(id);
        }

        try {
            dictTypeService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 添加字典类型
     * @param dictTypeVo
     * @return
     */
    //@RequiresPermissions("dictType:create")   //这个是测试细粒度的shiro
    @RequestMapping("addDictType")
    public ResultObj addDictType(DictTypeVo dictTypeVo) {
        try {
            dictTypeService.save(dictTypeVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 更新字典类型
     * @param dictTypeVo
     * @return
     */
    @RequestMapping("updateDictType")
    public ResultObj updateDictType(DictTypeVo dictTypeVo) {
        try {
            dictTypeService.updateById(dictTypeVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


}

