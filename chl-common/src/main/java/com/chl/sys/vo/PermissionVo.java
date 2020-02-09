package com.chl.sys.vo;

import com.chl.sys.pojo.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: chl
 * @Date: 2020/1/4 21:40
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //分页用到的
    private Integer page;
    private Integer limit;

    //批量删除的
    private Integer[] ids;
}
