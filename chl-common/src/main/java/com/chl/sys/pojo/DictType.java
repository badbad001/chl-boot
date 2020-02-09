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
@TableName("sys_dict_type")
@ApiModel(value="DictType对象", description="")
public class DictType implements Serializable {

    private static final long serialVersionUID=1L;

    //默认是雪花算法的id
    @ApiModelProperty(value = "字典类型ID")
    @TableId(value = "TYPE_ID",type = IdType.ID_WORKER_STR)
    private String typeId;

    @ApiModelProperty(value = "字典类型编码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "字典类型名字")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "备注")
    @TableField("MEMO")
    private String memo;

    @ApiModelProperty(value = "排序码【为了调整显示顺序】")
    @TableField("SORT")
    private Integer sort;


}
