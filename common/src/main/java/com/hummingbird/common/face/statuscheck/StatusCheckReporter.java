/**
 * 
 * StatusCheckReporter.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face.statuscheck;

/**
 * @author john huang
 * 2015年4月17日 上午8:43:51
 * 本类主要做为 报告的显示器
 */
public interface StatusCheckReporter {

	/**
	 * 生成报告
	 * @param result
	 * @return
	 */
	String report(StatusCheckResult result);
	
}
