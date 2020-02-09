package com.chl.sys.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.sys.common.constant.SysConstant;
import com.chl.sys.common.result.ResultObj;
import com.chl.sys.common.utils.DataGridView;
import com.chl.sys.common.utils.TreeNode;
import com.chl.sys.pojo.Permission;
import com.chl.sys.pojo.Role;
import com.chl.sys.service.PermissionService;
import com.chl.sys.service.RoleService;
import com.chl.sys.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-05
 */
@RestController
@RequestMapping("role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 添加角色
     * @param roleVo
     * @return
     */
    @RequestMapping("addRole")
    public ResultObj addRole(RoleVo roleVo) {
        try {

            roleService.save(roleVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 删除角色
     */
    @RequestMapping("deleteRole")
    public ResultObj deleteRole(RoleVo roleVo) {
        try {
            roleService.removeById(roleVo.getRoleId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 更新角色
     * @param roleVo
     * @return
     */
    @RequestMapping("updateRole")
    public ResultObj updateRole(RoleVo roleVo) {
        try {
            roleService.updateById(roleVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 查询带分页带条件
     * @param roleVo
     * @return
     */
    @RequestMapping("queryAllRole")
    public DataGridView queryAllRole(RoleVo roleVo) {
        IPage<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        //构造查询条件
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRoleName()), "role_name",roleVo.getRoleName());
        queryWrapper.eq(roleVo.getAvailable()!=null,"available",roleVo.getAvailable());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()),"remark", roleVo.getRemark());
        roleService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 加载权限通过角色id
     * @return
     */
    @RequestMapping("initPermissionByRid")
    public DataGridView initPermissionByRid(RoleVo roleVo) {
        //1.先查询全部权限和菜单  查询可用的
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", SysConstant.AVAILABLE);
        List<Permission> allPermission = permissionService.list(queryWrapper);


        //2.接着查询角色自己有的权限
        //2.1用单表解决问题 ,先查询sys_role_permission的pids出来
        List<Integer> pids = roleService.queryRolePermissionId(roleVo.getRoleId());


        //2.2用权限ids查询权限
        List<Permission> rolePermission = null;
        if (pids != null && pids.size()>0) {   //如果中间表有数据再去in来查
            queryWrapper.in("per_id", pids);
            rolePermission =permissionService.list(queryWrapper);
        }else {  //中间表没有查出数据，这样角色权限就为空，直接new 一个即可
            rolePermission = new ArrayList<>();
        }

        //3.组装树节点
        List<TreeNode> treeNodes = new ArrayList<>();
        //3.1遍历全部  如果id相同，说明有权限，所以checkAttr设置为 "1"
        for (Permission p1 : allPermission) {
            String checkArr = "0";
            for (Permission p2 : rolePermission) {
                if (p1.getPerId() == p2.getPerId()) {
                    checkArr = "1";
                    break; //跳出循环
                }
            }
            //是否展开  因为权限的展开字段是null的，所以为null也设置为SPREAD_TRUE
            Boolean spread = (p1.getOpen()==null||p1.getOpen()==1)?SysConstant.SPREAD_TRUE:SysConstant.SPREAD_FALSE;

            //添加进节点集合
            treeNodes.add(new TreeNode(p1.getPerId(), p1.getPid(), p1.getPerName(), spread, checkArr));
        }

        return new DataGridView(treeNodes);
    }

    /**
     * 角色分配权限
     * @param roleVo
     * @return
     */
    @RequestMapping("dispatchRolePermission")
    public ResultObj dispatchRolePermission(RoleVo roleVo) {
        try {
            roleService.dispatchRolePermission(roleVo.getRoleId(),roleVo.getIds());
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }

}

