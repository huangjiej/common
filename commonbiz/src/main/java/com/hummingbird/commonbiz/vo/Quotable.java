/**
 * 
 */
package com.hummingbird.commonbiz.vo;

import java.util.Date;

/**
 * 可以限额处理接口
 * @author huangjiej_2
 * 2014年10月3日 下午9:59:28
 */
public interface Quotable {

	/**
	 * 金额，单位分
	 */
	public Integer getSum();
	
	/**
	 * appid
	 * @return
	 */
	public String getAppId();
	
	/**
	 * 买家
	 * @return
	 */
	public String getBuyerId();
	
	/**
	 * 商户
	 * @return
	 */
	public String getSellerId();
	
	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreateTime();
}
