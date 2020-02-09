package com.chl.sys.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.sys.common.constant.SysConstant;
import com.chl.sys.common.result.ResultObj;
import com.chl.sys.common.utils.DataGridView;
import com.chl.sys.common.utils.TreeNode;
import com.chl.sys.pojo.Permission;
import com.chl.sys.service.PermissionService;
import com.chl.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-04
 */
@RestController
@RequestMapping("permission")
public class PermissionController {


    @Autowired
    private PermissionService permissionService;

    /**
     * 加载左边的菜单树
     * @return
     */
    @RequestMapping("loadLeftPermissionJson")
    public DataGridView loadLeftMenuTreeJson() {

        //组装条件,只查询菜单的
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", SysConstant.TYPE_MENU);

        List<Permission> list = permissionService.list(queryWrapper );
        List<TreeNode> treeNodes = new ArrayList<>();
        //遍历菜单，组装节点
        for (Permission permission : list) {
            Integer id = permission.getPerId();
            Integer pid = permission.getPid();
            String title = permission.getPerName();  //getPerName()
            Boolean spread = permission.getOpen()==1?SysConstant.SPREAD_TRUE:SysConstant.SPREAD_FALSE;
            treeNodes.add(new TreeNode(id, pid, title, spread));
        }

        return new DataGridView(treeNodes);
    }

    /**
     * 添加权限
     * @param permissionVo
     * @return
     */
    @RequestMapping("addPermission")
    public ResultObj addPermission(PermissionVo permissionVo) {
        try {
            //添加的是权限类型
            permissionVo.setType(SysConstant.TYPE_PERMISSION);
            permissionService.save(permissionVo);

            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     * 删除权限
     * @param permissionVo
     * @return
     */
    @RequestMapping("deletePermission")
    public ResultObj deletePermission(PermissionVo permissionVo) {
        try {
            permissionService.removeById(permissionVo.getPerId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 更新权限
     * @param permissionVo
     * @return
     */
    @RequestMapping("updatePermission")
    public ResultObj updatePermission(PermissionVo permissionVo) {
        try {
            permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 查询所有权限
     * @param permissionVo
     * @return
     */
    @RequestMapping("queryAllPermission")
    public DataGridView queryAllPermission(PermissionVo permissionVo) {

        IPage<Permission> page = new Page<>(permissionVo.getPage(), permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        //只查权限
        queryWrapper.eq("type", SysConstant.TYPE_PERMISSION);
        //条件查询的
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPerCode()), "per_code", permissionVo.getPerCode());
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPerName()), "per_name", permissionVo.getPerName());
        //点击菜单，查询当前菜单的权限
        queryWrapper.eq(permissionVo.getPerId()!=null, "pid", permissionVo.getPerId());


        permissionService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 查询最大排序码
     * @return
     */
    @RequestMapping("getMaxOrderNum")
    public Map<String, Object> getMaxOrderNum() {

        //构造查询条件，倒叙排序
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        List<Permission> list = permissionService.list(queryWrapper );
        //拿最大的排序码
        Map<String, Object> map = new HashMap<>();
        map.put("value", list.get(0).getSort()+1);

        return map;
    }

}

