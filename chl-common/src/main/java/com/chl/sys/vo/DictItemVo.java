package com.chl.sys.vo;


import com.chl.sys.pojo.DictItem;
import com.chl.sys.pojo.DictType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class DictItemVo extends DictItem {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//分页的参数
	private Integer page;
	private Integer limit;

    //开始结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

	//批量删除要用的
	private Integer[] ids;

}
