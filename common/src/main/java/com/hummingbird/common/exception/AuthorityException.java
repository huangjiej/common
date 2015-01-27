/**
 * 
 * AuthorityException.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.exception;

/**
 * @author huangjiej_2
 * 2014年12月29日 下午11:39:29
 * 本类主要做为 业务权限异常
 */
public class AuthorityException extends BusinessException {

	/**
	 * 构造函数
	 */
	public AuthorityException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数
	 */
	public AuthorityException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数
	 */
	public AuthorityException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数
	 */
	public AuthorityException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数
	 */
	public AuthorityException(int errcode, String message) {
		super(errcode, message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 复制一个新的异常
	 * @param e
	 * @return
	 */
	public AuthorityException clone(Throwable e){
		AuthorityException validateException = new AuthorityException(this.errcode, this.getMessage(), e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	/**
	 * 复制一个新的异常并用新的内容进行替换原有内容
	 * @param e
	 * @return
	 */
	public AuthorityException clone(Throwable e,String errmsg){
		AuthorityException validateException = new AuthorityException(this.errcode, errmsg, e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	/**
	 * 复制一个新的异常并用新的内容追加原有内容
	 * @param e
	 * @return
	 */
	public AuthorityException cloneAndAppend(Throwable e,String errmsg){
		AuthorityException validateException = new AuthorityException(this.errcode,this.getMessage(), e);
		validateException.appendMessage(errmsg);
		validateException.baseException=this.baseException;
		return validateException;
	}

	public AuthorityException(int errcode, String message, Throwable cause) {
		super(errcode, message, cause);
		// TODO Auto-generated constructor stub
	}

}
