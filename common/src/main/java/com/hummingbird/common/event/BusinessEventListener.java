/**
 * 
 * BusinessEventListener.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.event;

import java.util.EventListener;

/**
 * @author huangjiej_2
 * 2015年1月30日 下午6:08:32
 * 本类主要做为 业务事件处理器
 */
public interface BusinessEventListener extends EventListener {

	/**
	 * 事件处理
	 * @param event
	 */
	public void handleEvent(BusinessEvent event);
	
	
}
