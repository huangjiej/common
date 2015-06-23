/**
 * 
 * AbstractBusinessEventListener.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.event;


/**
 * @author huangjiej_2
 * 2015年1月30日 下午11:07:10
 * 本类主要做为
 */
public abstract class AbstractBusinessEventListener  implements BusinessEventListener {

	protected org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());

}
