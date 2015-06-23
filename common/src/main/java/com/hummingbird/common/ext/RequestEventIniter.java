/**
 * 
 * RequestEventIniter.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.ext;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.hummingbird.common.event.EventListenerContainer;
import com.hummingbird.common.event.StatusCheckerBusinessEventListener;
import com.hummingbird.common.util.PropertiesUtil;

/**
 * @author john huang
 * 2015年6月17日 下午11:54:46
 * 本类主要做为 请求event监听 初始化器
 */
public class RequestEventIniter implements AppIniter {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	/* (non-Javadoc)
	 * @see com.hummingbird.common.ext.AppIniter#init(java.util.Map)
	 */
	@Override
	public void init(Map param) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("请求event监听 初始化开始"));
		}
		//使用spel进行初始化,支持构建函数
		String requesteventclasses = new PropertiesUtil().getProperty("requestevent.init.classes");
		if(StringUtils.isNotBlank(requesteventclasses))
		{
			String[] requesteventarr = StringUtils.split(requesteventclasses, ",");	
			ExpressionParser parser = new SpelExpressionParser();
			for (int i = 0; i < requesteventarr.length; i++) {
				String requesteventclass = requesteventarr[i];
				if (log.isDebugEnabled()) {
					log.debug(String.format("初始化event:%s",requesteventclass));
				}
				try {
					StatusCheckerBusinessEventListener re = new StatusCheckerBusinessEventListener(requesteventclass);
					EventListenerContainer.getInstance().addMyListener(re);
				} catch (Exception e) {
					log.error(String.format("event[%s]初始化失败",requesteventclass),e);
				}
//				try {
//					StatusCheckerBusinessEventListener re = parser.parseExpression(requesteventclass).getValue(StatusCheckerBusinessEventListener.class); 
////				Class.forName(requesteventclass).getConstructor(String.class)
//					EventListenerContainer.getInstance().addMyListener(re);
//				} catch (Exception e) {
//					log.error(String.format("event[%s]初始化失败",requesteventclass),e);
//				}
			}
		}
		else{
			if (log.isDebugEnabled()) {
				log.debug(String.format("没有需要进行初始化"));
			}
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("请求event监听 初始化完成"));
		}

	}

}
