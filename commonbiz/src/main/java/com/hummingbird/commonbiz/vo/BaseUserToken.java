/**
 * 
 */
package com.hummingbird.commonbiz.vo;

/**
 * @author huangjiej_2
 * 2014年10月16日 上午7:18:35
 */
public class BaseUserToken implements UserToken {

	private String mobileNum;
	private String appId;
	private String token;
//	private int 
	/**
	 * 有效期
	 */
	private int expirein;

	
	
	public BaseUserToken(String appId, String mobileNum, String token,
			int expirein) {
		super();
		this.appId = appId;
		this.mobileNum = mobileNum;
		this.token = token;
		this.expirein = expirein;
	}

	public BaseUserToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseUserToken(String appId, String mobileNum, String token) {
		super();
		this.appId = appId;
		this.mobileNum = mobileNum;
		this.token = token;
	}

	/* (non-Javadoc)
	 * @see com.pay2b.vo.UserToken#getToken()
	 */
	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return token;
	}

	/* (non-Javadoc)
	 * @see com.pay2b.vo.UserToken#getAppId()
	 */
	@Override
	public String getAppId() {
		// TODO Auto-generated method stub
		return appId;
	}

	/* (non-Javadoc)
	 * @see com.pay2b.vo.UserToken#getMobile()
	 */
	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int getExpirein() {
		return expirein;
	}

	public void setExpirein(int expirein) {
		this.expirein = expirein;
	}

}
