/**
 * 
 * EventContainer.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huangjiej_2
 * 2015年1月30日 下午11:09:55
 * 本类主要做为
 */
public class EventListenerContainer {

	
	protected static EventListenerContainer self = null;
	
	public static EventListenerContainer getInstance(){
		if(self==null)
		{
			ReentrantLock lock = new ReentrantLock();
			try{
				lock.lock();
				if(self==null)
				{
					self = new EventListenerContainer();
				}
			}
			finally{
				lock.unlock();
				
			}
		}
		return self;
	}
	
	/**
	 * 监听器
	 */
	protected List<BusinessEventListener>  listenerList = new ArrayList();
	
	
	/** 
     * 添加一个MyListener监听器 
     */ 
    public void addMyListener(BusinessEventListener listener){ 
        listenerList.add(listener); 
    }

    /** 
     * 移除一个已注册的MyListener监听器. 
     * 如果监听器列表中已有相同的监听器listener1、listener2, 
     * 并且listener1==listener2, 
     * 那么只移除最近注册的一个监听器。 
     */ 
    public void removeMyListener(BusinessEventListener listener){ 
        listenerList.remove(listener); 
    }
    
    /**
     * 触发(处理)事件
     * @param event
     */
    public void fireEvent(BusinessEvent event) { 
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// getListenerList() Guaranteed to return a non-null array 
				for (Iterator iterator = listenerList.iterator(); iterator.hasNext();) {
					BusinessEventListener listener = (BusinessEventListener) iterator
							.next();
					listener.handleEvent(event);
				}
				
			}
		}).start();
   }
    
   public List<BusinessEventListener> getListeners(){
	   return listenerList;
   }
    
	
}
