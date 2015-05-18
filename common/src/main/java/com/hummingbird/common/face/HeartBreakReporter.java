/**
 * 
 * HeartBreakReporter.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face;

import com.hummingbird.common.face.statuscheck.StatusCheckResult;

/**
 * @author john huang
 * 2015年5月17日 下午10:07:12
 * 本类主要做为 心跳报告器
 */
public interface HeartBreakReporter {

	/**
	 * 生成心跳报告
	 * @return
	 */
	StatusCheckResult report();

}
