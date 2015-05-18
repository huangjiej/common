/**
 * 
 * HeartBreakSender.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.RequestException;
import com.hummingbird.common.face.statuscheck.StatusCheckResult;
import com.hummingbird.common.util.JsonUtil;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.http.HttpRequester;

/**
 * @author john huang
 * 2015年5月17日 下午9:47:04
 * 本类主要做为心跳初始化处理器
 */
public class HeartBreakIniter {

	Timer timer = new Timer();
	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	public void initHeartBreak(){
		PropertiesUtil pu = new PropertiesUtil();
		
		String heartbreakclass = pu.getProperty("heartbreak.class");
		if(StringUtils.isNotBlank(heartbreakclass)){
			
			String heartbreakurl = pu.getProperty("heartbreak.url");
			int cycle = pu.getInt("heartbreak.cycle",15*60000);//毫秒
			if (log.isDebugEnabled()) {
				log.debug(String.format("创建定时心跳任务,处理类%s,路径%s,心跳周期%s毫秒",heartbreakclass,heartbreakurl,cycle));
			}
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					try {
						HeartBreakReporter heartbreakreporter = (HeartBreakReporter) Class.forName(heartbreakclass).newInstance();
						StatusCheckResult result = heartbreakreporter.report();
						String convert2Json = JsonUtil.convert2Json(result);
						new HttpRequester().postRequest(heartbreakurl, convert2Json);
					} catch (Exception e) {
						log.error(String.format("心跳处理出错，不能发送心跳"),e);
					}
					
				}
			}, 60000, cycle);
			
		}
		
		
		
	}
	
	
}
