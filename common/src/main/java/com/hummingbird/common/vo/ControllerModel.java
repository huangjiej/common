/**
 * 
 * ControllerModel.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.vo;

import com.hummingbird.common.face.AbstractAppLog;

/**
 * @author john huang
 * 2015年10月30日 下午12:51:53
 * 本类主要做为 接口控制模型
 */
public class ControllerModel {

	/**
	 * json字符串,从request获取
	 */
	private String jsonstr;
	/**
	 * 日志
	 */
	private AbstractAppLog applog;
	
	
	/**
	 * 结果对象
	 */
	private ResultModel resultModel;
	/**
	 * 参数对象,从json字符串转换
	 */
	private Object transorder;
	/**
	 * json字符串从request获取 
	 */
	public String getJsonstr() {
		return jsonstr;
	}
	/**
	 * json字符串从request获取 
	 */
	public void setJsonstr(String jsonstr) {
		this.jsonstr = jsonstr;
	}
	/**
	 * 结果对象 
	 */
	public ResultModel getResultModel() {
		return resultModel;
	}
	/**
	 * 结果对象 
	 */
	public void setResultModel(ResultModel resultModel) {
		this.resultModel = resultModel;
	}
	/**
	 * 参数对象从json字符串转换 
	 */
	public Object getTransorder() {
		return transorder;
	}
	/**
	 * 参数对象从json字符串转换 
	 */
	public void setTransorder(Object transorder) {
		this.transorder = transorder;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ControllerModel [jsonstr=" + jsonstr + ", resultModel=" + resultModel + ", transorder=" + transorder
				+ "]";
	}
	/**
	 * 日志 
	 */
	public AbstractAppLog getApplog() {
		return applog;
	}
	/**
	 * 日志 
	 */
	public void setApplog(AbstractAppLog applog) {
		this.applog = applog;
	}



}
