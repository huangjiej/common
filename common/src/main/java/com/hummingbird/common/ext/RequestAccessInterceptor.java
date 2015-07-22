package com.hummingbird.common.ext;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.RequestUtil;

/**
 * controller 访问控制器拦截器
 * @author huangjiej_2
 * 2014年10月25日 下午9:57:10
 */
public class RequestAccessInterceptor extends HandlerInterceptorAdapter {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String requestIp = getIpAddr(request);
		//log.info("访问的地址是" + requestIp);
		
		PropertiesUtil pu = new PropertiesUtil();
		String allowIps = pu.getProperty("request.access.allow");
		String[] allowIpArr = extractRegExps(allowIps);
		
		
		String denyIps = pu.getProperty("request.access.deny");
		String[] denyIpArr = extractRegExps(denyIps);
		if (hasMatch(requestIp, denyIpArr)) {
			handleInvalidAccess(request, response, requestIp);
			return false;
		}

		if ((allowIpArr.length > 0) && !hasMatch(requestIp, allowIpArr)) {
			handleInvalidAccess(request, response, requestIp);
			return false;
		}
		return super.preHandle(request, response, handler);
	}
	
	private boolean hasMatch(String clientAddr, String[] regExps) {
		for (int i = 0; i < regExps.length; i++) {
			if (clientAddr.matches(regExps[i]))
				return true;
		}
		

		return false;
	}
	

	private String[] extractRegExps(String initParam) {
		if (initParam == null||initParam.trim().equals("")) {
			return new String[0];
		} else {
			return initParam.trim().split(",");
		}
	}
	
	private void handleInvalidAccess(ServletRequest request,
			ServletResponse response, String clientAddr) throws IOException {
//		String url = ((HttpServletRequest) request).getRequestURL().toString();
//		((HttpServletResponse) response)
//				.sendError(HttpServletResponse.SC_FORBIDDEN);
		if(log.isInfoEnabled()){
			log.info(String.format("客户机地址%s不允许访问本服务", clientAddr));
		}
		RequestUtil.writeOutput((HttpServletResponse)response,String.format("{\"errcode\":\"%s\",\"errmsg\":\"%s\"}",ValidateException.ERROR_PREMISSION_DENIED.getErrcode(),"IP地址访问限制"));
	}

	/**
	 * 获取客户端IP地址
	 */

	protected String getIpAddr(HttpServletRequest request) {
		// String ip = request.getHeader("x-forwarded-for");
		// if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		// {
		// ip = request.getHeader("Proxy-Client-IP");
		// }
		// if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		// {
		// ip = request.getHeader("WL-Proxy-Client-IP");
		// }
		// if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		// {
		// ip = request.getRemoteAddr();
		// }
		// return ip;

		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	

	
	
	
}
