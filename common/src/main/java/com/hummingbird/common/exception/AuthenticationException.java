package com.hummingbird.common.exception;

/**
 * 认证异常
 * @author huangjiej_2
 * 2014年10月12日 上午12:48:37
 */
public class AuthenticationException extends ValidateException {

	public AuthenticationException() {
		super();
	}



	public AuthenticationException(int errcode, String message, Throwable cause) {
		super(errcode, message, cause);
		// TODO Auto-generated constructor stub
	}



	public AuthenticationException(int errcode, String message) {
		super(errcode, message);
	}



	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 复制一个新的异常
	 * @param e
	 * @return
	 */
	public AuthenticationException clone(Throwable e){
		AuthenticationException validateException = new AuthenticationException(this.errcode, this.getMessage(), e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	/**
	 * 复制一个新的异常并用新的内容进行替换原有内容
	 * @param e
	 * @return
	 */
	public AuthenticationException clone(Throwable e,String errmsg){
		AuthenticationException validateException = new AuthenticationException(this.errcode, errmsg, e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	/**
	 * 复制一个新的异常并用新的内容追加原有内容
	 * @param e
	 * @return
	 */
	public AuthenticationException cloneAndAppend(Throwable e,String errmsg){
		AuthenticationException validateException = new AuthenticationException(this.errcode,this.getMessage(), e);
		validateException.appendMessage(errmsg);
		validateException.baseException=this.baseException;
		return validateException;
	}

}
