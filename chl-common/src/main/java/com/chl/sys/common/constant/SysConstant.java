package com.chl.sys.common.constant;

/**
 * @Author: chl
 * @Date: 2020/1/4 17:50
 * @Version 1.0
 */
public interface SysConstant {

    Integer OK = 200;
    Integer ERROR = -1;

    //未授权
    Integer UNAUTHORIZED = 403;

    //可用的
    Integer AVAILABLE = 1;

    //权限类型，菜单和权限
    String TYPE_MENU = "menu";
    String TYPE_PERMISSION = "permission";

    //是否展开
    Boolean SPREAD_FALSE = false;
    Boolean SPREAD_TRUE = true;

    //跟节点
    Integer BASE_ID = 1;

    //用户类型，管理员和普通用户
    Integer USER_TYPE_SUPER = 0;
    Integer USER_TYPE_NORMAL = 1;

    //密码加密
    Integer HASH_ITERATIONS_TWO = 2;  //加密次数2
    String DEFAULT_PASSWORD = "123456"; //默认密码

    //文件上传的临时文件后缀
    String FILE_UPLOAD_TEMP_SUFFIX = "_temp";

    //默认图片
    String DEFULT_IMG = "img/defaultImg.jpg";

    //默认用户头像图片
    String DEFULT_USER_IMG = "img/defaultUserImg.jpg";

    //是否选择数据表格的项
    Boolean LAY_CHECKED_TRUE = true;
    Boolean LAY_CHECKED_FALSE = false;

    //导出模板文件
    String EXPORT_TEMPLATE_PATH = "classpath:exportTemplates/";

}
