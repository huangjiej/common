/**
 * 
 * RemoteHostFilter.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hummingbird.common.exception.ValidateException;

/**
 * @author john huang 2015年7月10日 下午3:38:35 本类主要做为 ip地址过滤器
 * 使用方法为添加以这filter,并且设置要过滤的ip地址
 * <filter>
 *        <filter-name>hostFilter</filter-name>
 *       <filter-class>com.hummingbird.common.util.RemoteHostFilter</filter-class>
 *       <init-param>
 *           <param-name>allow</param-name>
 *           <param-value></param-value>
 *       </init-param>
 *       <init-param>
 *           <param-name>deny</param-name>
 *           <param-value></param-value>
 *       </init-param>
 *   </filter>
 */
public class RemoteHostFilter implements Filter {

	
	
	private String[] allow;
	private String[] deny;

	private FilterConfig filterConfig = null;

	public RemoteHostFilter() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String clientAddr = request.getRemoteAddr();

		if (hasMatch(clientAddr, deny)) {
			handleInvalidAccess(request, response, clientAddr);
			return;
		}

		if ((allow.length > 0) && !hasMatch(clientAddr, allow)) {
			handleInvalidAccess(request, response, clientAddr);
			return;
		}

		chain.doFilter(request, response);
	}

	private void handleInvalidAccess(ServletRequest request,
			ServletResponse response, String clientAddr) throws IOException {
//		String url = ((HttpServletRequest) request).getRequestURL().toString();
//		((HttpServletResponse) response)
//				.sendError(HttpServletResponse.SC_FORBIDDEN);
		
		RequestUtil.writeOutput((HttpServletResponse)response,String.format("{\"errcode\":\"%s\",\"errmsg\":\"%s\"}",ValidateException.ERROR_PREMISSION_DENIED.getErrcode(),"IP地址访问限制"));
	}

	private boolean hasMatch(String clientAddr, String[] regExps) {
		for (int i = 0; i < regExps.length; i++) {
			if (clientAddr.matches(regExps[i]))
				return true;
		}

		return false;
	}

	public void destroy() {
		this.filterConfig = null;
		this.allow = null;
		this.deny = null;
	}

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		this.allow = extractRegExps(filterConfig.getInitParameter("allow"));
		this.deny = extractRegExps(filterConfig.getInitParameter("deny"));
	}

	private String[] extractRegExps(String initParam) {
		if (initParam == null||initParam.trim().equals("")) {
			return new String[0];
		} else {
			return initParam.trim().split(",");
		}
	}

	public String toString() {
		if (filterConfig == null)
			return ("ClientAddrFilter()");
		StringBuffer sb = new StringBuffer("ClientAddrFilter(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());
	}

}
