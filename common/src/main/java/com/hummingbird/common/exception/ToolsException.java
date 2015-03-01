/**
 * 
 * ToolsException.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.exception;

/**
 * @author john huang
 * 2015年3月1日 下午4:36:35
 * 本类主要做为工具类等公用异常
 */
public class ToolsException extends BusinessException {

	public ToolsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ToolsException(int errcode, String message, Throwable cause) {
		super(errcode, message, cause);
		// TODO Auto-generated constructor stub
	}

	public ToolsException(int errcode, String message) {
		super(errcode, message);
		// TODO Auto-generated constructor stub
	}

	public ToolsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ToolsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ToolsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
