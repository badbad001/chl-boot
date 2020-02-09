package com.chl.sys.web.listener;

import com.chl.sys.common.properties.Project;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 这个用来获取项目的真实路径和这个监听器
 * 在全局容器创建完成，将project数据放到项目
 */
@WebListener
public class AppListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Autowired
    private Project project;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//取到ServletContext
		ServletContext context=arg0.getServletContext();

		//放一些需要放的东西进去 例如project的东西
        context.setAttribute("project",project);
		System.err.println("---------Servlet容器创建成功 project数据被放到ServletContext作用域-------");
	}

}
