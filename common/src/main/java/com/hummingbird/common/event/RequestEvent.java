/**
 * 
 * RequestEvent.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.event;

/**
 * @author john huang
 * 2015年6月17日 上午12:06:54
 * 本类主要做为 请求event
 */
public class RequestEvent implements BusinessEvent  {

	/**
	 * 源
	 */
	protected Object source;
	/**
	 * 请求类型
	 */
	protected String requestType;
	/**
	 * 是否成功
	 */
	protected boolean successed;
	
	public RequestEvent(Object source, String requestType, boolean successed) {
		super();
		this.source = source;
		this.requestType = requestType;
		this.successed = successed;
	}

	/**
	 * @return
	 */
	public boolean isSuccessed(){
		return successed;
	}

	public RequestEvent(Object source) {
		super();
		this.source = source;
	}

	public RequestEvent(Object source, boolean successed) {
		super();
		this.source = source;
		this.successed = successed;
	}

	/**
	 * @return the source
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Object source) {
		this.source = source;
	}

	/**
	 * @param successed the successed to set
	 */
	public void setSuccessed(boolean successed) {
		this.successed = successed;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestEvent [source=" + source + ", requestType="
				+ requestType + ", successed=" + successed + "]";
	}
	
	
	
	
}
