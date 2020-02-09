package com.chl.sys.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.sys.common.constant.SysConstant;
import com.chl.sys.common.constant.SysConstant;
import com.chl.sys.common.result.ResultObj;
import com.chl.sys.common.utils.DataGridView;
import com.chl.sys.common.utils.TreeNodeBuilder;
import com.chl.sys.common.utils.TreeNode;
import com.chl.sys.common.utils.WebUtils;
import com.chl.sys.pojo.Permission;
import com.chl.sys.pojo.User;
import com.chl.sys.service.PermissionService;
import com.chl.sys.service.RoleService;
import com.chl.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("menu")
public class MenuController {



	@Autowired
	private PermissionService permissionService;

    @Autowired
    private RoleService roleService;


	/**
	 * 加载页面左边菜单数
	 * @param permissionVo
	 * @return
	 */
	@RequestMapping("loadLeftMenuTreeJson")
	public List<TreeNode> loadLeftMenuTreeJson(PermissionVo permissionVo) {
		//1.构建条件查询
		//1.1查询是菜单类型的和可用的
		QueryWrapper<Permission> wrapper = new QueryWrapper<>();
		wrapper.eq("available", SysConstant.AVAILABLE);
		wrapper.eq("type",SysConstant.TYPE_MENU);

		//权限集合
		List<Permission> list = null;

		//2.判断是管理员还是普通用户
		//User user = (User) WebUtils.getHttpSession().getAttribute("user");
        User user = WebUtils.getCurrentUser();
        if (user.getType() == SysConstant.USER_TYPE_SUPER) {
			list = permissionService.list(wrapper);
		}else {
			//2.2根据用户id查询菜单列表,因为考虑到mycat,所以就单表操作
            //2.3先根据用户id查询sys_role_user的角色id集合
            List<Integer> rids = roleService.queryUserRoleIdsByUid(user.getUserId());

            //2.4根据角色id查询权限id集合
            Set<Integer> pidSet = new HashSet<>();
            for (Integer rid : rids) {
                List<Integer> pids = roleService.queryRolePermissionId(rid);
                //把查到的每个角色所拥有权限，放进set集合里面来去重
                pidSet.addAll(pids);
            }

            //2.5根据去重后的权限id集合来查询权限集合
            //这个把去重后的pidSet  用in()来查询权限
            //去重后的权限set集合不为空，且大于0才in查询，不然
            if (pidSet!=null && pidSet.size()>0) {
                wrapper.in("per_id", pidSet);
                list = permissionService.list(wrapper);
            }else {  //这个用户没有菜单,就直接new一个空的即可
                list = new ArrayList<>();
            }

		}


		//2.遍历集合，构建基础节点
		List<TreeNode> baseNodes = new ArrayList<>();
		for (Permission permission : list) {
			Integer id = permission.getPerId();
			Integer pid = permission.getPid();
			String icon = permission.getIcon();
			String href = permission.getHref();
			String title = permission.getPerName();
			Boolean spread = permission.getOpen() == 1? SysConstant.SPREAD_TRUE : SysConstant.SPREAD_FALSE;
			baseNodes.add(new TreeNode(id, pid, icon, href, title, spread));
		}

		 //3.构造层级关系的节点  跟节点设置为1
		List<TreeNode> rtnNodes = TreeNodeBuilder.bulidTree(baseNodes,SysConstant.BASE_ID);

		//返回页面
		return rtnNodes;

	}

	/**
	 * 加载左边的菜单
	 */
	@RequestMapping("loadLeftMenuJson")
	public DataGridView loadLeftMenuJson() {

		//构建查询条件，只查询菜单类型的
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type", SysConstant.TYPE_MENU);

		List<Permission> list = permissionService.list(queryWrapper);
		List<TreeNode> treeNodes = new ArrayList<>();

		//遍历菜单集合，组装节点
		for (Permission permission : list) {
			Integer id = permission.getPerId();
			Integer pid = permission.getPid();
			String title = permission.getPerName();
			//这个是是否展开
			Boolean spread = permission.getOpen() == 1?SysConstant.SPREAD_TRUE:SysConstant.SPREAD_FALSE;

			treeNodes.add(new TreeNode(id, pid, title, spread));
		}

		return new DataGridView(treeNodes);

	}

	/**
	 * 添加菜单
	 * @param permissionVo
	 * @return
	 */
	@RequestMapping("addMenu")
	public ResultObj addMenu(PermissionVo permissionVo) {
		//设置是菜单类型
		permissionVo.setType(SysConstant.TYPE_MENU);

        //防止页面传过来空字符窜地址，置空即可
        if (StringUtils.isBlank(permissionVo.getHref())){
            permissionVo.setHref(null);
        }

		try {
			permissionService.save(permissionVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}

	/**
	 * 删除
	 * @param permissionVo
	 * @return
	 */
	@RequestMapping("deleteMenu")
	public ResultObj deleteMenu(PermissionVo permissionVo) {
		try {
			permissionService.removeById(permissionVo.getPerId());
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}


	/**
	 * 更新菜单
	 * @param permissionVo
	 * @return
	 */
	@RequestMapping("updateMenu")
	public ResultObj updateMenu(PermissionVo permissionVo) {
		try {
		    //防止页面传过来空字符窜地址，置空即可
		    if (StringUtils.isBlank(permissionVo.getHref())){
		        permissionVo.setHref(null);
            }
			permissionService.updateById(permissionVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}

	/**
	 * 查询菜单带条件带分页
	 * @param permissionVo
	 * @return
	 */
	@RequestMapping("queryAllMenu")
	public DataGridView queryAllMenu(PermissionVo permissionVo) {

		IPage<Permission> page = new Page<>(permissionVo.getPage(), permissionVo.getLimit());
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();

		//模糊查询的
		queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPerName()),"per_name", permissionVo.getPerName());
		//只查菜单类型的
		queryWrapper.eq("type", SysConstant.TYPE_MENU);
		//点击查菜单，和子菜单
		if (permissionVo.getPerId()!=null) {
			queryWrapper.and(i->i.eq("per_id", permissionVo.getPerId())
				    .or().eq("pid", permissionVo.getPerId()));
		}

		//按排序码排序
		queryWrapper.orderByAsc("sort");

		permissionService.page(page, queryWrapper);

		return new DataGridView(page.getTotal(),page.getRecords());

	}

	/**
	 * 获取最大的排序码
	 * @return
	 */
	@RequestMapping("getMaxOrderNum")
	public Map<String, Object> getMaxOrderNum() {

		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		//构造条件，按照排序码排序降序
	    queryWrapper.orderByDesc("sort");
		List<Permission> list = permissionService.list(queryWrapper);

		Map<String, Object> map = new HashMap<>();
		//拿最大那个加1返回去
	    map.put("value",list.get(0).getSort()+1);
	    return map;
	}

	/**
	 * 检查是否有子菜单
	 * @param permissionVo
	 * @return
	 */
	@RequestMapping("checkHasChildMenu")
	public Map<String, Object> checkHasChildMenu(PermissionVo permissionVo) {

		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid", permissionVo.getPerId());

		int count = permissionService.count(queryWrapper );

		Map<String, Object> map =new HashMap<>();
		if (count>0) {
			map.put("value", true);
		}else {
			map.put("value", false);
		}

		return map;
	}

}
