/**
 * 
 * ConvertionException.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.exception;

import com.hummingbird.common.exception.BusinessException;

/**
 * @author john huang
 * 2015年5月21日 上午10:15:00
 * 本类主要做为转换异常
 */
public class ConvertionException extends BusinessException{

	public ConvertionException() {
		super();
		errcode=BusinessException.ERRCODE_CONVERSION;
		// TODO Auto-generated constructor stub
	}

	public ConvertionException(int errcode, String message, Throwable cause) {
		super(errcode, message, cause);
		errcode=BusinessException.ERRCODE_CONVERSION;
		// TODO Auto-generated constructor stub
	}

	public ConvertionException(int errcode, String message) {
		super(errcode, message);
		errcode=BusinessException.ERRCODE_CONVERSION;
		// TODO Auto-generated constructor stub
	}

	public ConvertionException(String message, Throwable cause) {
		super(message, cause);
		errcode=BusinessException.ERRCODE_CONVERSION;
		// TODO Auto-generated constructor stub
	}

	public ConvertionException(String message) {
		super(message);
		errcode=BusinessException.ERRCODE_CONVERSION;
		// TODO Auto-generated constructor stub
	}

	public ConvertionException(Throwable cause) {
		super(cause);
		errcode=BusinessException.ERRCODE_CONVERSION;
		// TODO Auto-generated constructor stub
	}

}
