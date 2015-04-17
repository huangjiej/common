/**
 * 
 * StatusCheckResult.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face.statuscheck;

import java.util.List;

/**
 * @author john huang
 * 2015年4月17日 上午8:40:27
 * 本类主要做为 状态检查结果
 */
public interface StatusCheckResult {

	/**
	 * 状态级别，分为3分，2-异常，需要马上报警，1-报告，可能有问题，或存在一些需要报告的内容，这也需要报告，由运维判断是否处理，0-正常，不需要报告
	 */
	public int getStatusLevel();
	
	public String getResultReport();
	
	/**
	 * 获取子状态检查结果
	 * @return
	 */
	public List<StatusCheckResult> getSubStatusCheckResult();
	
	/**
	 * 功能/项目名称
	 * @return
	 */
	public String getFuncname();
	
}
