/**
 * 
 * FailStatModel.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.vo;

import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.ArrayUtils;

import com.hummingbird.common.event.RequestEvent;
import com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult;
import com.hummingbird.common.face.statuscheck.StatusCheckResult;

/**
 * @author john huang
 * 2015年6月13日 上午7:33:34
 * 本类主要做为 失败统计模型
 */
public class RequestStatModel {
	
	protected org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());

	/**
	 * 监控的名称
	 */
	protected String name;

	/**
	 * 超过多少毫秒认为是失败
	 */
	protected long recvReqSpan = 0;
	
	/**
	 * 构造函数
	 * @name 名称
	 * @failprecent 千分比
	 */
	public RequestStatModel(String name,int failprecent){
		this.name = name;
		maxerrorpenc = failprecent;
	}
	/**
	 * 构造函数
	 * @name 名称
	 * @failprecent 千分比
	 */
	public RequestStatModel(String name,int failprecent,long recvReqSpan){
		this.name = name;
		maxerrorpenc = failprecent;
		this.recvReqSpan  = recvReqSpan;
	}
	
	/**
	 * 最近一次接收到数据的时间
	 */
	protected long lastReceiveTimestamp = -1;
	
	public void addSuccess(){
		addResult(true);
	}
	
	public void addFail(){
		addResult(false);
	}
	
	/**
	 * 添加结果
	 * @param flag
	 */
	public void addResult(boolean flag)
	{
		lastReceiveTimestamp = System.currentTimeMillis();
		totalcount++;
		if(!flag)
			failinastat++;
		pcountinstat--;
		if(pcountinstat<=0){
			ReentrantLock rl = new ReentrantLock();
			try{
				rl.lock();
				//统计，按1000条
				calcularStat();
			}
			finally{
				rl.unlock();
			}
		}
		
	}
	
	/**
	 * 总记录数
	 */
	protected long totalcount=0 ;
	
	/**
	 * 用于统计内容，失败百份比
	 */
	protected Stack<Integer> failprecent = new Stack<Integer>();
	/**
	 * 记录组统计的失败条数
	 */
	protected Stack<Integer> failcount = new Stack<Integer>();
	/**
	 * 计数器,每100条进行一次计数
	 */
	protected int countinstat = 100;
	
	/**
	 * 动态变化的计数器
	 */
	protected int pcountinstat = countinstat;
	
	/**
	 * 一次统计中失败的条数
	 */
	protected int failinastat = 0;
	
	/**
	 * 按多少组进行统计，一组为100条，一般统计的组越多，越精确
	 */
	protected int statgroup =3 ;
	
	/**
	 * 允许的最大的失败率
	 */
	protected  Integer maxerrorpenc=600;
	
	/**
	 * 执行统计
	 */
	private void calcularStat() {
		if(pcountinstat>0){
			//已处理过，不再统计
			return ;
		}
		int index = 1;//统计第几组的数据
		int totalfailcount = failinastat;
		for (Iterator iterator = failcount.iterator(); iterator.hasNext();) {
			Integer pfcount = (Integer) iterator.next();
			totalfailcount+=pfcount;
			index++;
			if(index>statgroup)
			{
				break;
			}
		}
		failcount.push(failinastat);//把当前的失败数当成一组放入
		failinastat=0;
		pcountinstat=countinstat;
		//统计比例,千份比
		int pfailprecent =0;
		if(index==0){
			index=1;//没有从之前的分组中拿到数据时,则为第1组
		}
		pfailprecent =totalfailcount*1000/countinstat/(index);
		
		failprecent.push(pfailprecent);//把当前的失败率当成一组放入
		if(log.isInfoEnabled()){
			log.info(String.format("最近%s条支付成功率为%s‰",countinstat*index,pfailprecent));
		}
	}
	
//	public StatusCheckResult getStatusResult() {
//	StatusCheckResult sr = new StatusCheckResult();
//	if(failprecent.size()<4){
//		if(failprecent.size()>0){
//			Integer precent = failprecent.peek();
//			if(precent>maxerrorpenc){
//				sr.setErr(1, "错误率已超过阀值达:"+precent+"‰");
//				sr.setStatusLevel(2);
//				return sr;
//			}
//		}
//		//样本太少不能预测
//		sr.setErrmsg("正常");
//		return sr;
//	}
//	
//	int tempprecent=0;
//	
//	for (Iterator iterator = failprecent.iterator(); iterator.hasNext();) {
//		Integer precent = (Integer) iterator.next();
//		if(precent<tempprecent){
//			sr.setErrmsg("正常");
//			return sr;
//		}
//		tempprecent=precent;
//	}
//	//错误率持续升高
//	sr.setErrmsg("错误率呈现上升趋势，最近的错误率为"+failprecent.peek()+"‰");
//	sr.setStatusLevel(1);
//	if(failprecent.peek()>maxerrorpenc){
//		sr.setErr(1, "错误率已超过阀值达:"+failprecent.peek()+"‰");
//		sr.setStatusLevel(2);
//	}
//	
//	return sr;
//}
	
	/**
	 * 获取上一次的时间间隔
	 * @return
	 */
	public com.hummingbird.common.face.statuscheck.StatusCheckResult getRequestSpan(){
		AbstractStatusCheckResult sr = new AbstractStatusCheckResult();
		sr.setFunctionName("请求的时间间隔");
		if (log.isDebugEnabled()) {
			log.debug(String.format("统计请求的时间间隔,上一次请求时间为%s",lastReceiveTimestamp));
		}
		if(lastReceiveTimestamp<0){
			if (log.isDebugEnabled()) {
				log.debug("至今为止没有收到过请求");
			}
			sr.setReport("至今为止没有收到过请求");
		}
		else{
			if(recvReqSpan>0){
				if(System.currentTimeMillis()-lastReceiveTimestamp > recvReqSpan)
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("上一次请求时间距现在大于%s毫秒,报告异常",recvReqSpan));
					}
					sr.setStatusLevel(2);
				}
			}
			sr.setReport(((System.currentTimeMillis()-lastReceiveTimestamp)/1000)+"秒前有业务处理");
		}
		return sr;
	}
	

	/**
	 * 查询失败的百分比率
	 * @return
	 */
	public com.hummingbird.common.face.statuscheck.StatusCheckResult getFailPrecent(){
		AbstractStatusCheckResult sr = new AbstractStatusCheckResult();
		sr.setFunctionName("查询失败百分比");
		if (log.isDebugEnabled()) {
			log.debug(String.format("统计查询失败百分比"));
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("失败统计统计组数为:%s",failprecent));
		}
		sr.setReport("正常");
		if(failprecent.size()<4){
			if(failprecent.size()>0){
				Integer precent = failprecent.peek();
				if (log.isDebugEnabled()) {
					log.debug(String.format("失败统计统计组数小于4组,直接取最近的失败数:%s",precent));
				}
				sr.setReport("错误率:"+precent+"‰");
				if(precent>maxerrorpenc){
					if (log.isDebugEnabled()) {
						log.debug(String.format("失败统计率已超过阀值达:%s‰",precent));
					}
					sr.setReport("错误率已超过阀值达:"+precent+"‰");
					sr.setStatusLevel(2);
					return sr;
				}
			}
			else if(failinastat>0){
				Integer precent=0;
				if(countinstat==pcountinstat){
					precent = failinastat*1000/countinstat;
				}
				else{
					precent = failinastat*1000/(countinstat-pcountinstat);
				}
				if (log.isDebugEnabled()) {
					log.debug(String.format("统计当前数据的失败率,记录数=%s,失败条数=%s,失败率=%s",(countinstat-pcountinstat==0?countinstat:countinstat-pcountinstat),failinastat,precent));
				}
				sr.setReport("错误率:"+precent+"‰");
				if(precent>maxerrorpenc){
					if (log.isDebugEnabled()) {
						log.debug(String.format("失败统计率已超过阀值达:%s‰",precent));
					}
					sr.setReport("错误率已超过阀值达:"+precent+"‰");
					sr.setStatusLevel(2);
					return sr;
				}
			}
			//样本太少不能预测
			
			return sr;
		}
		
		int tempprecent=0;
		if (log.isDebugEnabled()) {
			log.debug(String.format("查看失败率是否呈上升趋势"));
		}
		Object[] array = failprecent.toArray();
		array=ArrayUtils.subarray(array, array.length-4, array.length);
		for (int i = 0; i < array.length; i++) {
			Integer precent = (Integer)array[i];
			if(precent<tempprecent){
				if (log.isDebugEnabled()) {
					log.debug(String.format("查看失败率有下降,属于正常"));
				}
				sr.setReport("正常,最近的错误率为"+array[array.length-1]+"‰");
				return sr;
			}
			tempprecent=precent;
		}
//		for (Iterator iterator = failprecent.iterator(); iterator.hasNext();) {
//			Integer precent = (Integer) iterator.next();
//			if(precent<tempprecent){
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("查看失败率有下降,属于正常"));
//				}
//				sr.setReport("正常");
//				return sr;
//			}
//			tempprecent=precent;
//		}
		//错误率持续升高
		if (log.isDebugEnabled()) {
			log.debug("错误率呈现上升趋势，最近的错误率为"+failprecent.peek()+"‰");
		}
		sr.setReport("错误率呈现上升趋势，最近的错误率为"+failprecent.peek()+"‰");
		sr.setStatusLevel(1);
		Integer lastfailprecent = failprecent.peek();
		if(lastfailprecent==null){lastfailprecent=0;}
		if(lastfailprecent>maxerrorpenc){
			if (log.isDebugEnabled()) {
				log.debug(String.format("失败统计率已超过阀值达:%s‰",lastfailprecent));
			}
			sr.setReport("错误率已超过阀值达:"+lastfailprecent+"‰");
			sr.setStatusLevel(2);
		}
		return sr;
	}
	/**
	 * 处理结果
	 * @param reqe
	 */
	public void addResult(RequestEvent reqe) {
		addResult(reqe.isSuccessed());
		
	}
	/**
	 * @return the countinstat
	 */
	public int getCountinstat() {
		return countinstat;
	}
	/**
	 * @param countinstat the countinstat to set
	 */
	public void setCountinstat(int countinstat) {
		this.countinstat = countinstat;
		pcountinstat=countinstat;
	}
	
	public static void main(String[] args) {
		//测试模型
		RequestStatModel rsm = new RequestStatModel("测试", 600);
		rsm.setCountinstat(7);
		Boolean[] records = {true,true,false,false,false,true,true,
				false,false,false,false,true,true,true
				,true,true,true,true,true,false,true
				,true,true,true,true,true,false,true
				,true,true,true,true,true,false,true
				,true,true,true,true,false,false,true
				,true,true,false,false,false,false,true
				,true,true,false,false,false,false,true
				,false,false,false,false,false,false,true
		};
		//50 , 7/12,66.7
		for (int i = 0; i < records.length; i++) {
			Boolean flag = records[i];
			rsm.addResult(flag);
			if(i%7==0){
				
				StatusCheckResult failPrecent2 = rsm.getFailPrecent();
				if(!failPrecent2.isNormal()){
					System.out.println("第"+((i/7)+1)+"组异常:"+failPrecent2);
				}
				else{
					System.out.println("第"+((i/7)+1)+"组正常:"+failPrecent2);
					
				}
			}
		}
	}

}
