package com.hummingbird.commonbiz.vo;

/**
 * 用户令牌，一个appid和一个手机号码确定一个令牌
 * @author huangjiej_2
 * 2014年10月13日 下午10:52:12
 */
public interface UserToken extends AppMobile {

	/**
	 * 令牌
	 * @return
	 */
	public String getToken();
	
	/**
	 * 应用ID
	 * @return
	 */
	public String getAppId();
	
	/**
	 * 手机号
	 * @return
	 */
	public String getMobileNum();
	
	/**
	 * 有效期，单位秒
	 * @return
	 */
	public int getExpirein();

	/**
	 * 更新手机号
	 * @param mobilenum
	 */
	public void setMobileNum(String mobilenum);
	
	
}
