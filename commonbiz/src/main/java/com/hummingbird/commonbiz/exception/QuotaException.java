package com.hummingbird.commonbiz.exception;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;

/**
 * 限额异常
 * @author huangjiej_2
 * 2014年9月27日 下午4:48:58
 */
public class QuotaException extends ValidateException {

	public QuotaException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuotaException(int errcode, String message) {
		super(errcode, message);
		// TODO Auto-generated constructor stub
	}

	public QuotaException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public QuotaException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public QuotaException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

	public QuotaException(int errcode, String message, Throwable cause) {
		super(errcode, message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 复制一个新的异常
	 * @param e
	 * @return
	 */
	public ValidateException clone(Throwable e){
		return new QuotaException(this.errcode, this.getMessage(), e);
	}
	
}
