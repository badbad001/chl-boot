package com.chl.sys.web.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.chl.sys.common.bean.ActiveUser;
import com.chl.sys.common.result.ResultObj;
import com.chl.sys.common.utils.WebUtils;
import com.chl.sys.pojo.Loginfo;
import com.chl.sys.pojo.User;
import com.chl.sys.service.LoginfoService;
import com.chl.sys.service.UserService;
import com.chl.sys.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: chl
 * @Date: 2020/1/4 17:11
 * @Version 1.0
 */
@RequestMapping("login")
@RestController
public class LoginController {

    @Autowired
    private LoginfoService loginfoService;

    @Autowired
    private UserService userService;

    /**
     * 简单登录不走shiro
     * @return
     */
    @RequestMapping("simpleLogin")
    public ResultObj simple(UserVo userVo){
        //传过来的验证码
        String pageCode = userVo.getCode();

        //获取session域的验证码
        String code = (String) WebUtils.getHttpSession().getAttribute("code");

        //获取完验证码，立即移除，弄成一次性验证码
        HttpSession session = WebUtils.getHttpSession();
        session.removeAttribute("code");

        //两个验证码不匹配
        if (!StringUtils.equals(pageCode,code)) {
            return ResultObj.CODE_ERROR;
        }

        User existUser = userService.login(userVo.getLoginName(), userVo.getPwd());

        if (existUser != null){
            session.setAttribute("user",existUser);

            //记录登录日志
            Loginfo loginfo = new Loginfo();

            loginfo.setLoginIp( WebUtils.getHttpServletRequest().getRemoteAddr());
            loginfo.setLoginName(existUser.getUserName());
            loginfo.setLoginTime(new Date());

            loginfoService.save(loginfo);
            return ResultObj.LOGIN_SUCCESS;
        }else {
            return ResultObj.LOGIN_ERROR;
        }


    }

    /**
     * 登录
     * @param userVo
     * @return
     */
    @RequestMapping("login")
    public ResultObj login(UserVo userVo ) {

        //传过来的验证码
        String pageCode = userVo.getCode();

        //获取session域的验证码
        String code = (String) WebUtils.getHttpSession().getAttribute("code");

        //两个验证码不匹配
        if (!StringUtils.equals(pageCode,code)) {
            return ResultObj.CODE_ERROR;
        }

        //构建tocken
        UsernamePasswordToken token = new UsernamePasswordToken(userVo.getLoginName(), userVo.getPwd());
        //获取subject接口
        Subject subject = SecurityUtils.getSubject();

        try {
            //执行登录
            subject.login(token);

            //登录成功放进session域
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();

            WebUtils.getHttpSession().setAttribute("user",activeUser.getUser());

            //记录登录日志
            Loginfo loginfo = new Loginfo();

            loginfo.setLoginIp( WebUtils.getHttpServletRequest().getRemoteAddr());
            loginfo.setLoginName(activeUser.getUser().getUserName());
            loginfo.setLoginTime(new Date());

            loginfoService.save(loginfo);

            return ResultObj.LOGIN_SUCCESS;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResultObj.LOGIN_ERROR;
        }

    }


    /**
     * 切换验证码
     */
    @RequestMapping("getCode")
    public void getCode(HttpSession session, HttpServletResponse response){
        //创建一个验证码获取对象
        //四个参数意思分别是验证码图片的宽高,验证码个数，扰乱码个数
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,5);
        //把验证码存session
        session.setAttribute("code",lineCaptcha.getCode());
        //然后把图片写回页面

        try {
            //把图片写到页面
            lineCaptcha.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
