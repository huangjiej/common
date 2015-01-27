package com.hummingbird.common.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量结果集
 * @author huangjiej_2
 * 2014-5-31 下午04:26:45
 */
public class BatchResultModel extends ResultModel {

	/**
	 * 数据名
	 */
	protected String countname = "totalcount";
	
	/**
	 * 错误信息集
	 * @return
	 */
	public List<ResultModel> getError() {
		return error;
	}

	/**
	 * 失败记录总数
	 */
	protected int failedCount;
	
	/**
	 * 处理的总记录数
	 */
	protected int count;
	/**
	 * 处理的总记录数
	 */
	public int getCount() {
		Integer cn = (Integer) this.get(countname);
		if(cn==null){
			return 0;
		}
		return cn;
	}
	/**
	 * 处理的总记录数
	 */
	public void setCount(int count) {
		this.count = count;
		this.put(countname,count);
	}

	protected List<ResultModel> error = new ArrayList<ResultModel>();

	private List<ResultModel> successes= new ArrayList<ResultModel>();


	/**
	 * 失败记录总数
	 */
	public int getFailedCount() {
		Integer failedcount = (Integer) this.get("failedCount");
		if(failedcount==null){
			failedcount=0;
			this.put("failedCount", 0);
		}
		return failedcount;
	}

	/**
	 * 失败记录总数
	 */
	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
		this.put("failedCount",failedCount);
	}
	
	public BatchResultModel(String msg){
		super();
		this.put("errmsg", msg);
		this.put("error", error);
	}
	
	public void addResultModel(ResultModel rm){
		this.error.add(rm);
		failedCount++;
		this.put("failedCount",failedCount);
		errcode=1;
		this.setErrcode(1);
		if(null!=this.errmsg){
			this.errmsg=this.errmsg.replace("成功", "失败");
			this.put("errmsg", errmsg);
		}
		this.put("error", error);
	}
	
	/**
	 * 作为成功添加
	 * @param rm
	 */
	public void addResultModelAsSuccess(ResultModel rm){
		this.successes.add(rm);
	}
	
	/**获取成功的数据
	 * @return
	 */
	public List<ResultModel> getSuccesses() {
		return successes;
	}
	public BatchResultModel(){
		super();
		
	}
	
	/**
	 * 设置总记录数条数
	 * @param countName
	 */
	public void setCountName(String countName){
		if(this.containsKey(countname)){
			
			int tcount = getCount();
			this.remove(countname);
			this.countname=countName;
			this.setCount(tcount);
		}
		else{
			this.countname=countName;
		}
	}

	@Override
	public String toString() {
		return "BatchResultModel [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", countname=" + countname + ", failedCount=" + failedCount
				+ ", error=" + error + "]";
	}
	
	
}
