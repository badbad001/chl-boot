package com.chl.sys.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("sys_permission")
@ApiModel(value="Permission对象", description="")
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "菜单权限id")
    @TableId(value = "per_id", type = IdType.AUTO)
    private Integer perId;

    @ApiModelProperty(value = "父ID")
    private Integer pid;

    @ApiModelProperty(value = "权限类型[menu/permission]")
    private String type;

    @ApiModelProperty(value = "菜单或者权限名")
    private String perName;

    @ApiModelProperty(value = "权限编码[只有type= permission才有  user:view]")
    private String perCode;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "地址链接")
    private String href;

    @ApiModelProperty(value = "目标")
    private String target;

    @ApiModelProperty(value = "【是否打开树用于dTree,0打开1不打开】")
    private Integer open;

    @ApiModelProperty(value = "备注")
    private String remark;

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


}
