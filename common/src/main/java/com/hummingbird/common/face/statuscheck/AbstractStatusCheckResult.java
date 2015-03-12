/**
 * 
 * AbstractStatusCheckResult.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face.statuscheck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.vo.StatusCheckResult;

/**
 * @author john huang
 * 2015年3月5日 下午6:54:25
 * 本类主要做为状态报告类，这个类
 */
public abstract class AbstractStatusCheckResult implements IStatusCheckResult,StatusCheckItemResult {

	 
	/**
	 * 状态级别，分为3分，2-异常，需要马上报警，1-报告，可能有问题，或存在一些需要报告的内容，这也需要报告，由运维判断是否处理，0-正常，不需要报告
	 */
	protected int statusLevel=0;
	
	protected String report;
	/**
	 * 功能名称
	 */
	protected String functionName="未命名";
	
	protected List<StatusCheckItemResult> items = new ArrayList<StatusCheckItemResult>();
	
	
	/* (non-Javadoc)
	 * @see com.hummingbird.common.face.statuscheck.IStatusCheckResult#getStatusLevel()
	 */
	public int getStatusLevel() {
		//return statusLevel;
		int max=statusLevel;
		if(max==2){
			return max;
		}
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			StatusCheckItemResult statusCheckItemResult = (StatusCheckItemResult) iterator
					.next();
			if(statusCheckItemResult.getStatusLevel()>max){
				max = statusCheckItemResult.getStatusLevel();
				if(max==2){
					return max;
				}
			}
		}
		return max;
	}
	
	public String getReport(){
		return report;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.common.face.statuscheck.IStatusCheckResult#getResultReport()
	 */
	@Override
	public String getResultReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(functionName );
		sb.append("\"状态报告:" );
		int sl = getStatusLevel();
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
		if(StrUtil.isNotBlank(report)){
			sb.append("-" );
			sb.append(report );
		}
		sb.append("\n" );
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			StatusCheckItemResult statusCheckItemResult = (StatusCheckItemResult) iterator
					.next();
			String report2 = statusCheckItemResult.getReport();
			sb.append(report2);
			sb.append("\n" );
		}
		return sb.toString();
	}

	
	public void addItem(StatusCheckItemResult item){
		this.items.add(item);
	}
	
	public List<StatusCheckItemResult> getItemResults(){
		return items;
	}

}
