package com.chl.sys.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈汉龙
 * @since 2020-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户登录名")
    private String loginName;

    @ApiModelProperty(value = "用户地址")
    private String address;

    @ApiModelProperty(value = "用户性别【0女,1男】")
    private Integer sex;

    @ApiModelProperty(value = "用户备注简介")
    private String remark;

    @ApiModelProperty(value = "用户密码")
    private String pwd;

    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    @ApiModelProperty(value = "用户类型【0超级管理员,1普通用户】")
    private Integer type;

    @ApiModelProperty(value = "头像地址")
    private String userImgPath;

    @ApiModelProperty(value = "用户的盐(用于shiro)")
    private String salt;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "是否可用【0不可用1可用】")
    private Integer available;

    @ApiModelProperty(value = "排序码【为了调整显示顺序】")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "部门名字")
    @TableField(exist = false)
    private String deptName;

    //技术集合
    @TableField(exist = false)
    @ApiModelProperty(value = "部门名字")
    private List<Integer> tids;
}
