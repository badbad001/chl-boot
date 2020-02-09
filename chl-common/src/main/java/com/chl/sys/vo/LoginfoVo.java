package com.chl.sys.vo;

import com.chl.sys.pojo.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: chl
 * @Date: 2020/1/4 21:39
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginfoVo extends Loginfo {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //分页用到的
    private Integer page;
    private Integer limit;

    //批量删除的
    private Integer[] ids;

    //开始结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
