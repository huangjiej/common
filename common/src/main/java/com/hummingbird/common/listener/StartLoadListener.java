package com.hummingbird.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 启动初始化
 * @author huangjiej_2
 * 2014年9月27日 上午9:15:06
 */
public class StartLoadListener implements ServletContextListener {
	private static final Log log = LogFactory.getLog(StartLoadListener.class);

	public void contextDestroyed(ServletContextEvent sce) {
		// System.out.println("contextDestroyed.....");
	}

	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
		com.hummingbird.common.util.SpringBeanUtil.init(ac);
		
	}

}
