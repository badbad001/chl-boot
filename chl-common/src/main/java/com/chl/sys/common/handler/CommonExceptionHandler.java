package com.chl.sys.common.handler;

import com.chl.sys.common.result.ResultObj;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice //用controller这个返回字符串，跳转到页面
//@RestControllerAdvice  //rest这个返回json
public class CommonExceptionHandler {

    /**
     * 捕获到403异常
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public String hanler403Exception(){
    	return "sys/exception/403";  //跳到模板的页面
        //return ResultObj.UNAUTHORIZED;
    }
}
