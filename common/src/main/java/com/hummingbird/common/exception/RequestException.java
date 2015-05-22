package com.hummingbird.common.exception;

/**
 * 远程访问，请求异常
 * @author huangjiej_2
 * 2014年11月12日 下午4:28:59
 */
public class RequestException extends BusinessException{

	public RequestException() {
		super();
		errcode=BusinessException.ERRCODE_REQUEST;
	}

	public RequestException(int errcode, String message) {
		super(errcode, message);
		errcode=BusinessException.ERRCODE_REQUEST;
		// TODO Auto-generated constructor stub
	}

	public RequestException(String message, Throwable cause) {
		super(message, cause);
		errcode=BusinessException.ERRCODE_REQUEST;
		// TODO Auto-generated constructor stub
	}

	public RequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RequestException(Throwable cause) {
		super(cause);
		errcode=BusinessException.ERRCODE_REQUEST;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 复制一个新的异常
	 * @param e
	 * @return
	 */
	public RequestException clone(Throwable e){
		RequestException validateException = new RequestException(this.errcode, this.getMessage(), e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	/**
	 * 复制一个新的异常并用新的内容进行替换原有内容
	 * @param e
	 * @return
	 */
	public RequestException clone(Throwable e,String errmsg){
		RequestException validateException = new RequestException(this.errcode, errmsg, e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	/**
	 * 复制一个新的异常并用新的内容追加原有内容
	 * @param e
	 * @return
	 */
	public RequestException cloneAndAppend(Throwable e,String errmsg){
		RequestException validateException = new RequestException(this.errcode,this.getMessage(), e);
		validateException.appendMessage(errmsg);
		validateException.baseException=this.baseException;
		return validateException;
	}

	public RequestException(int errcode, String message, Throwable cause) {
		super(errcode, message, cause);
	}
	
}
