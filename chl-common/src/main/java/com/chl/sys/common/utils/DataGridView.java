package com.chl.sys.common.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: badbad
 * @Date: 2019/9/22 23:33
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class DataGridView {
    private Integer code = 0;
    private String msg = "";
    private Long count ;
    private Object data;

    public DataGridView(Object data) {
        this.data = data;
    }

    /*这个提供给页面分页的构造*/
    public DataGridView(Long count, Object data) {
        this.count = count;
        this.data = data;
    }
}
