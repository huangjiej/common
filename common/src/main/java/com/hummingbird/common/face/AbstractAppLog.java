package com.hummingbird.common.face;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author john huang
 * 2015年10月30日 下午3:12:15
 * 本类主要做为 基于app的日志,它会放在
 */
public class AbstractAppLog {
	
    /**
     * 应用id
     */
    protected String appid;

    /**
     * 执行方法
     */
    protected String method;

    /**
     * 请求参数
     */
    protected String request;

    /**
     * 响应参数
     */
    protected String respone;

    /**
     * 执行时间
     */
    protected Date inserttime;


   
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractAppLog [appid=" + appid + ", method=" + method + ", request=" + request + ", respone=" + respone
				+ ", inserttime=" + inserttime + "]";
	}



	/**
	 * 应用id 
	 */
	public String getAppid() {
		return appid;
	}



	/**
	 * 应用id 
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}



	/**
	 * 执行方法 
	 */
	public String getMethod() {
		return method;
	}



	/**
	 * 执行方法 
	 */
	public void setMethod(String method) {
		this.method = method;
	}



	/**
	 * 请求参数 
	 */
	public String getRequest() {
		return request;
	}



	/**
	 * 请求参数 
	 */
	public void setRequest(String request) {
		this.request = request;
	}



	/**
	 * 响应参数 
	 */
	public String getRespone() {
		return respone;
	}



	/**
	 * 响应参数 
	 */
	public void setRespone(String respone) {
		this.respone = respone;
	}



	/**
	 * 执行时间 
	 */
	public Date getInserttime() {
		return inserttime;
	}



	/**
	 * 执行时间 
	 */
	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
}