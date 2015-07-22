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
 * 本类主要做为html报告器
 */
public class HtmlReporter implements StatusCheckReporter{

	/* (non-Javadoc)
	 * @see com.hummingbird.common.face.statuscheck.StatusCheckReporter#report(com.hummingbird.common.face.statuscheck.StatusCheckResult)
	 */
	@Override
	public String report(StatusCheckResult result) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div>");
		sb.append("\"");
		sb.append(result.getFuncname() );
		sb.append("\"状态报告:" );
		sb.append("<b style=\"color:red\">");
		int sl = result.getStatusLevel();
		switch (sl) {
		case 2:
			sb.append("运行异常" );
			break;
		case 1:
			sb.append("运行警报" );
			break;
		case 0:
			sb.append("运行正常" );
			break;

		default:
			break;
		}
		sb.append("</b>");
		sb.append("</div>");
		String reportStr = getReportStr(result,true);
		sb.append("<div>" );
		sb.append(reportStr);
		sb.append("</div>" );
		return sb.toString();
	}

	/**
	 * @param result
	 * @param sb
	 */
	public String getReportStr(StatusCheckResult result,boolean noheader) {
		StringBuilder sb = new StringBuilder();
		sb.append("<dl>");
		if(!noheader){
			sb.append("<dt>");
			sb.append(result.getFuncname());
			sb.append("-");
			String statusLevelCN = "";
			switch (result.getStatusLevel()) {
			case 0:
				statusLevelCN= "正常";
				break;
			case 1:
				statusLevelCN =  "告警";
				break;
			case 2:
				statusLevelCN = "警报";
				break;
			default:
				return "未知";
			}
			sb.append("<b style=\"color:red\">");
			sb.append(statusLevelCN);
			sb.append("</b>");
			sb.append("</dt>");
		}
		
		if(StringUtils.isNotBlank(result.getResultReport())){
			sb.append("<dd>");
			sb.append(result.getResultReport() );
			sb.append("</dd>");
		}
		sb.append("<dl>");
		List<StatusCheckResult> items = result.getSubStatusCheckResult();
		if(!items.isEmpty()){
			sb.append("<ol>");
			for (Iterator iterator = items.iterator(); iterator.hasNext();) {
				StatusCheckResult statusCheckItemResult = (StatusCheckResult) iterator
						.next();
				sb.append("<li>");
				sb.append(getReportStr(statusCheckItemResult,false));
				sb.append("</li>");
			}
			sb.append("</ol>");
		}
		
		return sb.toString();
	}
	

}
