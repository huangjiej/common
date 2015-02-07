/**
 * 
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.vo;

/**
 * @author huangjiej_2
 * 2014年12月14日 下午1:04:23
 * 本类主要做为状态监控的状态结果
 */
public class StatusCheckResult extends ResultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4122744529367891186L;
	/**
	 * 状态级别，分为3分，2-异常，需要马上报警，1-报告，可能有问题，或存在一些需要报告的内容，这也需要报告，由运维判断是否处理，0-正常，不需要报告
	 */
	private int statusLevel=0;

	/**
	 * 状态级别，分为3分，2-异常，需要马上报警，1-报告，可能有问题，或存在一些需要报告的内容，这也需要报告，由运维判断是否处理，0-正常，不需要报告
	 */
	public int getStatusLevel(){
		return statusLevel;
	}

	/**
	 * 状态级别，分为3分，2-异常，需要马上报警，1-报告，可能有问题，或存在一些需要报告的内容，这也需要报告，由运维判断是否处理，0-正常，不需要报告
	 */
	public void setStatusLevel(int statusLevel) {
		this.statusLevel = statusLevel;
	}

	public StatusCheckResult() {
		super();
	}

	public StatusCheckResult(Exception e) {
		super(e);
	}

	public StatusCheckResult(int errcode, String errmsg) {
		super(errcode, errmsg);
	}

	public StatusCheckResult(String msg) {
		super(msg);
	}

	public StatusCheckResult(String msg,int statusLevel ) {
		super(msg);
		this.statusLevel = statusLevel;
	}
	
	public StatusCheckResult(int errcode,String msg,int statusLevel ) {
		super(msg);
		this.statusLevel = statusLevel;
	}
	
	
	
	
}
