package com.chl.sys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
 * @since 2020-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict_item")
@ApiModel(value="DictItem对象", description="")
public class DictItem implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "字典项ID")
    @TableId(value = "ITEM_ID",type = IdType.ID_WORKER_STR)
    private String itemId;

    @ApiModelProperty(value = "字典类型ID")
    @TableField("TYPE_ID")
    private String typeId;

    @ApiModelProperty(value = "父字典ID")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "字典编码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "字典名字")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "排序码")
    @TableField("SORT")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    @TableField("MEMO")
    private String memo;

    @ApiModelProperty(value = "是否启用")
    @TableField("IS_ENABLED")
    private Boolean isEnabled;

    @ApiModelProperty(value = "扩展字段1")
    @TableField("RESERVED1")
    private String reserved1;

    @ApiModelProperty(value = "扩展字段2")
    @TableField("RESERVED2")
    private String reserved2;

    @ApiModelProperty(value = "扩展字段3")
    @TableField("RESERVED3")
    private String reserved3;


}
