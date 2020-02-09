package com.chl.sys.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.sys.common.constant.SysConstant;
import com.chl.sys.common.result.ResultObj;
import com.chl.sys.common.utils.DataGridView;
import com.chl.sys.common.utils.TreeNode;
import com.chl.sys.pojo.Dept;
import com.chl.sys.service.DeptService;
import com.chl.sys.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-05
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 加载左边部门json
     * @return
     */
    @RequestMapping("loadLeftDeptJson")
    public DataGridView loadLeftDeptJson(DeptVo deptVo) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();

        //查询可用的
        queryWrapper.eq("available", SysConstant.AVAILABLE);
        List<Dept> list = deptService.list(queryWrapper);

        //遍历部门，构建部门的树节点
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Dept dept : list) {
            Integer id  = dept.getDeptId();
            Integer pid = dept.getPid();
            String title = dept.getDeptName();
            Boolean spread = dept.getOpen() == 1?SysConstant.SPREAD_TRUE:SysConstant.SPREAD_FALSE;

            treeNodes.add(new TreeNode(id, pid, title, spread));
        }

        return new DataGridView(treeNodes);
    }

    /**
     * 查询部门带分页带条件
     * @return
     */
    @RequestMapping("queryAllDept")
    public DataGridView queryAllDept(DeptVo deptVo) {

        IPage<Dept> page = new Page<>(deptVo.getPage(), deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<Dept>();

        //构造条件
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getDeptName()), "dept_name",deptVo.getDeptName());


        //这个是点击数，查当前节点，和字节点的
        queryWrapper.eq(deptVo.getDeptId()!=null, "dept_id", deptVo.getDeptId())
                .or().eq(deptVo.getDeptId()!=null,"pid", deptVo.getDeptId());

        deptService.page(page, queryWrapper);

        //查询父部门名字
        List<Dept> depts = page.getRecords();
        for (Dept dept : depts) {
            if (dept.getPid() == 0){
                dept.setPDeptName("无");
            }else {
                Dept pDept = deptService.getById(dept.getPid());
                dept.setPDeptName(pDept.getDeptName());
            }

        }

        System.out.println(depts);
        return new DataGridView(page.getTotal(), depts);

    }

    /**
     * 添加部门
     * @param deptVo
     * @return
     */
    @RequestMapping("addDept")
    public ResultObj addDept(DeptVo deptVo) {

        try {
            //设置部门创建时间
            deptService.save(deptVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 更新部门
     * @return
     */
    @RequestMapping("updateDept")
    public ResultObj updateDept(DeptVo deptVo) {

        try {
            deptService.updateById(deptVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 检查是否有子部门
     * @return
     */
    @RequestMapping("checkHasChildDept")
    public Map<String, Object> checkHasChildDept(DeptVo deptVo) {

        //构造条件
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", deptVo.getDeptId());

        //查询
        int count = deptService.count(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        //有子部门为true
        if (count>0) {
            map.put("value", true);
        }else {
            map.put("value", false);
        }

        return map;
    }

    /**
     * 删除部门
     * @param deptVo
     * @return
     */
    @RequestMapping("deleteDept")
    public ResultObj deleteDept(DeptVo deptVo) {
        try {
            deptService.removeById(deptVo.getDeptId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {

            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 获取最大的排序码
     * @return
     */
    @RequestMapping("getMaxOrderNum")
    public Map<String, Object> getMaxOrderNum() {

        //构造查询条件
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        List<Dept> list = deptService.list(queryWrapper);

        //降序排序拿到最大的id
        Map<String, Object> map = new HashMap<>();
        //接着加1返回去
        map.put("value", list.get(0).getSort()+1);
        return  map;
    }


}

