/**
 * 
 * AppMobileDecidable.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.commonbiz.vo;

/**
 * @author huangjiej_2
 * 2015年1月3日 下午3:24:35
 * 本类主要做为基于appid和mobile的验证对象
 */
public interface AppMobileDecidable extends Decidable {

	/**
	 * 应用id
	 * @return
	 */
	String getAppId();
	
	/**
	 * 手机号
	 * @return
	 */
	String getMobileNum();
	
}
