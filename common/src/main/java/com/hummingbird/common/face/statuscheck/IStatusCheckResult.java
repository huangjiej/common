/**
 * 
 * IStatusCheckResult.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face.statuscheck;

import com.hummingbird.common.vo.StatusCheckResult;

/**
 * @author john huang
 * 2015年3月5日 下午6:50:11
 * 本类主要做为
 */
public interface IStatusCheckResult {

	/**
	 * 状态级别，分为3分，2-异常，需要马上报警，1-报告，可能有问题，或存在一些需要报告的内容，这也需要报告，由运维判断是否处理，0-正常，不需要报告
	 */
	public int getStatusLevel();
	
	public String getResultReport();
	
	
	/**
	 * 生成状态报告
	 * @return
	 */
	public StatusCheckResult getStatusResult();
	
	
}
