package com.hummingbird.commonbiz.vo;

import com.hummingbird.common.util.json.JSONException;

/**
 * 通知接口
 * @author huangjiej_2
 * 2014年9月21日 上午11:03:10
 */
public interface Notifiable {

	/**
	 * 获取请求参数
	 * @return
	 * @throws JSONException 
	 */
	public String toJson() throws JSONException;

	/**
	 * 获取通知地址
	 * @return
	 */
	public abstract String getNotifyUrl();
	
	/**
	 * 通知描述
	 * @return
	 */
	public String getDesc();
	
	
}
