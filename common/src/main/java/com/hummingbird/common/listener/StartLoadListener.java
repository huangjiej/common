package com.hummingbird.common.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hummingbird.common.ext.AppIniter;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.StrUtil;

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
		
		Map param=new HashMap();
		String classes = new PropertiesUtil().getProperty("app.init.classes");
		if(StrUtil.isNotBlank(classes))
		{
			String[] zlasses = classes.split(",");
			for (int i = 0; i < zlasses.length; i++) {
				String zlass = zlasses[i];
				AppIniter initer;
				try {
					initer = (AppIniter) Class.forName(zlass).newInstance();
					initer.init(param);
				} catch (Exception e) {
					log.error(String.format("初始化出错"),e);
				}
				
			}
		}
		
	}

}
