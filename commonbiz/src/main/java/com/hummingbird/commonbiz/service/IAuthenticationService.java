/**
 * 
 */
package com.hummingbird.commonbiz.service;

import com.hummingbird.common.exception.AuthenticationException;
import com.hummingbird.commonbiz.vo.Decidable;

/**
 * 认证service
 * @author huangjiej_2
 * 2014年10月12日 上午12:42:18
 */
public interface IAuthenticationService {

	/**
	 * 对内容进行认证，它可能包括用户的请求，或者第三方（如博升的请求）
	 * @param authObj
	 * @return
	 */
	public Object validateAuth(Decidable authObj) throws AuthenticationException;
	
	
}
