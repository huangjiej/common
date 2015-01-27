/**
 * 
 * SmsMessage.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.commonbiz.face;

/**
 * @author huangjiej_2
 * 2015年1月26日 上午11:22:18
 * 本类主要做为 短信的顶级接口
 */
public interface SmsMessage {

	/**
	 * 获取手机号
	 * @return
	 */
	String getMobileNum();
	
	/**
	 * 获取内容
	 * @return
	 */
	String getContent();
	
	
}
