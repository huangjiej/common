/**
 * 
 * StatusCheckItemResult.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face.statuscheck;

import com.hummingbird.common.vo.StatusCheckResult;


/**
 * @author huangjiej_2
 * 2015年2月1日 下午11:45:31
 * 本类主要做为 状态报告项接口，一个状态报告可以拆分为多个项
 */
public interface StatusCheckItemResult extends IStatusCheckResult{
	
	/**
	 * 生成状态报告
	 * @return
	 */
	public StatusCheckResult getStatusResult();
	
	
	/**
	 * 获取文本报告
	 * @return
	 */
	public String getReport();
	
	
}
