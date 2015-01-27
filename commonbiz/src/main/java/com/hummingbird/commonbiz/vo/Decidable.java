/**
 * 
 */
package com.hummingbird.commonbiz.vo;

import java.util.Map;

import com.hummingbird.common.exception.SignatureException;

/**
 * 可被认证的对象
 * @author huangjiej_2
 * 2014年10月12日 上午12:47:18
 */
public interface Decidable {
	
	/**
	 * 认证的类型，现在有信用平台，用户2种
	 * @return
	 */
	int getType();
	
	/**
	 * 设置其它的参数
	 * @param map
	 */
	void setOtherParam(Map map);
	
	
	/**
	 * 是否认证成功
	 * @return
	 */
	boolean isAuthed() throws SignatureException ;
	

}
