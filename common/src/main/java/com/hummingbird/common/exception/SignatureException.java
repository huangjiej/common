package com.hummingbird.common.exception;


/***********************************************************************
 * Module:  SignatureException.java
 * Author:  huangjiej_2
 * Purpose: Defines the Class SignatureException
 ***********************************************************************/


/** 数字签名验证不正确抛出此异常 */
public class SignatureException extends ValidateException {

	public SignatureException(int errcode, String message, Throwable cause) {
		super(errcode, message, cause);
		// TODO Auto-generated constructor stub
	}

	public SignatureException() {
		
	}

	public SignatureException(int errcode, String message) {
		super(errcode, message);
		// TODO Auto-generated constructor stub
	}

	public SignatureException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SignatureException(String message) {
		super(ValidateException.ERRCODE_SIGNATURE_FAIL,message);
		// TODO Auto-generated constructor stub
	}

	public SignatureException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 复制一个新的异常
	 * @param e
	 * @return
	 */
	public SignatureException clone(Throwable e){
		SignatureException validateException = new SignatureException(this.errcode, this.getMessage(), e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	/**
	 * 复制一个新的异常并用新的内容进行替换原有内容
	 * @param e
	 * @return
	 */
	public SignatureException clone(Throwable e,String errmsg){
		SignatureException validateException = new SignatureException(this.errcode, errmsg, e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	/**
	 * 复制一个新的异常并用新的内容追加原有内容
	 * @param e
	 * @return
	 */
	public SignatureException cloneAndAppend(Throwable e,String errmsg){
		SignatureException validateException = new SignatureException(this.errcode,this.getMessage(), e);
		validateException.appendMessage(errmsg);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	
}