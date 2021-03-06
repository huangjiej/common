/**
 * 
 * SmsReporter.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face.statuscheck;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author john huang
 * 2015年4月17日 上午8:47:20
 * 本类主要做为短信报告器
 */
public class SmsReporter implements StatusCheckReporter{

	/* (non-Javadoc)
	 * @see com.hummingbird.common.face.statuscheck.StatusCheckReporter#report(com.hummingbird.common.face.statuscheck.StatusCheckResult)
	 */
	@Override
	public String report(StatusCheckResult result) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(result.getFuncname() );
		sb.append("\"状态报告:" );
		int sl = result.getStatusLevel();
		switch (sl) {
		case 2:
			sb.append("异常" );
			break;
		case 1:
			sb.append("警报" );
			break;
		case 0:
			sb.append("正常" );
			break;

		default:
			break;
		}
		String reportStr = getReportStr(result,true);
		
		sb.append(reportStr);
		
		return sb.toString();
	}

	/**
	 * @param result
	 * @param sb
	 */
	public String getReportStr(StatusCheckResult result,boolean noheader) {
		if(result.getStatusLevel()==0){
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		if(!noheader){
			
			sb.append("〖");
			sb.append(result.getFuncname());
			sb.append(":");
		}
		else{
			sb.append("【" );
		}
		
		
		if(StringUtils.isNotBlank(result.getResultReport())){
			sb.append(result.getResultReport() );
		}
		List<StatusCheckResult> items = result.getSubStatusCheckResult();
		if(!items.isEmpty()){
			
			for (Iterator iterator = items.iterator(); iterator.hasNext();) {
				StatusCheckResult statusCheckItemResult = (StatusCheckResult) iterator
						.next();
//				String report2 = statusCheckItemResult.getReport();
//				sb.append("【" );
				
				sb.append(getReportStr(statusCheckItemResult,false));
				
//				sb.append("】" );
			}
			
		}
		if(!noheader){
			sb.append("〗");
		}
		else{
			sb.append("】" );
		}
		return sb.toString();
	}
	

}
