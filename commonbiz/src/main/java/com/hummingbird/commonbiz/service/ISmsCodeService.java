package com.hummingbird.commonbiz.service;

import com.hummingbird.commonbiz.exception.TokenException;
import com.hummingbird.commonbiz.vo.UserToken;

/**
 * 短信验证码service
 * @author huangjiej_2
 * 2014年10月18日 下午12:17:49
 */
public interface ISmsCodeService {

	/**
	 * 验证用户令牌
	 * @param token
	 * @return
	 * @throws TokenException 
	 */
	public boolean validateToken(String appId, String mobileNum,String authcode) throws  TokenException;
	
	/**
	 * 创建用户令牌
	 * @param appId
	 * @param mobileNum
	 * @return
	 */
	public UserToken createToken(String appId,String mobileNum);
	
	/**
	 * 查询用户令牌
	 * @return
	 */
	public UserToken queryToken(String appId, String mobileNum);
	/**
	 * 创建用户令牌
	 * @param appId
	 * @param mobileNum
	 * @return
	 */
	UserToken createToken(String appId, String mobileNum, int len);
	
	
}
