/**
 * 
 * StatusCheckResultBusinessEventListener.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.event;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult;
import com.hummingbird.common.face.statuscheck.StatusCheckResult;
import com.hummingbird.common.face.statuscheck.StatusChecker;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.vo.RequestStatModel;

/**
 * @author john huang
 * 2015年6月12日 下午6:47:03
 * 本类主要做为 任务状态的监听器
 */
public class StatusCheckerBusinessEventListener extends
		AbstractBusinessEventListener implements StatusChecker {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	private String functionName;
	
	private String mappingFunctionName;

	public StatusCheckerBusinessEventListener(String name){
		
		String funcname = new PropertiesUtil().getProperty("statuscheck.func."+name);
		String mappingfuncname = new PropertiesUtil().getProperty("statuscheck.func.mapping."+name);
		if(StringUtils.isBlank(mappingfuncname)){
			mappingFunctionName = "ALL";
		}
		else{
			mappingFunctionName = ","+mappingfuncname+",";
		}
		int failprecent = new PropertiesUtil().getInt("statuscheck.failprecent."+name,50);
		int receivespan = new PropertiesUtil().getInt("statuscheck.receivespan."+name,30*60*1000);
		reqmodel = new RequestStatModel(funcname,failprecent,receivespan);
		this.functionName = funcname;
	}
	
	
	RequestStatModel reqmodel ;

	/* (non-Javadoc)
	 * @see com.hummingbird.common.event.BusinessEventListener#handleEvent(com.hummingbird.common.event.BusinessEvent)
	 */
	@Override
	public void handleEvent(BusinessEvent event) {
		if (event instanceof RequestEvent) {
			RequestEvent reqe = (RequestEvent) event;
			if("all".equalsIgnoreCase(mappingFunctionName)||mappingFunctionName.indexOf(","+reqe.getRequestType()+",")!=-1){
				
				reqmodel.addResult(reqe);
			}
			else{
//				log.d
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.common.face.statuscheck.StatusChecker#statusCheck()
	 */
	@Override
	public StatusCheckResult statusCheck() {
		AbstractStatusCheckResult sr = new AbstractStatusCheckResult(functionName);
		StatusCheckResult failPrecent = reqmodel.getFailPrecent();
		String report = "正常";
		sr.addItem(failPrecent);
		if(!failPrecent.isNormal()){
			report="异常";
		}
		StatusCheckResult requestSpan = reqmodel.getRequestSpan();
		sr.addItem(requestSpan);
		if(!requestSpan.isNormal()){
			report="异常";
		}
		sr.setReport(report);
		return sr;
	}
	
	

}
