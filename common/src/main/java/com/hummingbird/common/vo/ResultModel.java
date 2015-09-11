package com.hummingbird.common.vo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.BadSqlGrammarException;

import com.hummingbird.common.exception.BusinessException;
import com.hummingbird.common.exception.ValidateException;

/**
 * controller结果集
 * @author huangjiej_2
 * 2014-5-31 下午04:26:45
 */
public class ResultModel extends HashMap{

	public static final String KEY_ERRCODE="errcode";
	public static final String KEY_ERRMSG="errmsg";
	
	
			
	/**
	 * 错误号
	 */
	protected int errcode=0;
	
	/**
	 * 信息
	 */
	protected String errmsg;
	
	/**
	 * 开始时间
	 */
	protected long starttime = System.currentTimeMillis();
	/**
	 * 基础的错误值
	 */
	private int baseerrcode;
	

	/**
	 * 统计耗时
	 * @return
	 */
	public void printSpenttime(Log log){
		if(log!=null&&log.isDebugEnabled()){
			log.debug(getSpenttime());
		}
		else{
			System.out.println(getSpenttime());
		}
	}
	/**
	 * 统计耗时
	 * @return
	 */
	public String getSpenttime(){
		long spent = System.currentTimeMillis() - starttime;
		
		return String.format("执行耗时%s毫秒", spent);
	}
	
	/**
	 * 错误号
	 */
	public int getErrcode() {
		return this.containsKey("errcode")?(Integer)this.get("errcode"):0;
	}

	/**
	 * 错误号
	 */
	public void setErrcode(int errcode) {
		this.errcode=errcode;
		this.put("errcode", errcode);
	}

	/**
	 * 信息
	 */
	public String getErrmsg() {
		return this.containsKey("errmsg")?(String) this.get("errmsg"):"";
	}

	/**
	 * 信息
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
		this.put("errmsg", errmsg);
	}

	
	public void setErr(int errcode,String errmsg) {
		this.put("errcode",errcode);
		this.put("errmsg",errmsg);
		this.errcode=errcode;
		this.errmsg=errmsg;
	}

	public ResultModel(String msg){
		errmsg =msg;
		this.put("errmsg", msg);
		this.put("errcode", errcode);
		
	}
	
	public ResultModel(Exception e){
		
		mergeException(e);
		
	}

	public ResultModel() {
		this.put("errmsg", "");
		this.put("errcode", errcode);
	}
	
	public ResultModel(int errcode,String errmsg) {
		this.put("errcode",errcode);
		this.put("errmsg",errmsg);
	}

	/**
	 * 构造函数
	 */
	public ResultModel(Map map) {
		Object p_errcode = map.get(KEY_ERRCODE);
		Object p_errmsg = map.get(KEY_ERRMSG);
		this.setErrcode(NumberUtils.toInt(ObjectUtils.toString(p_errcode),1000));
		this.setErrmsg(ObjectUtils.toString(p_errmsg));
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ResultModel [");
		for (Iterator iterator = this.keySet().iterator(); iterator.hasNext();) {
			Map.Entry en = (Map.Entry) iterator.next();
			Object key = en.getKey();
			Object value = en.getValue();
			sb.append(key);
			sb.append("=");
			sb.append(value);
			sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 是否成功
	 * @return
	 */
	public boolean isSuccessed(){
		return this.errcode==0;
	}
	/**
	 * 把异常作为错误输出
	 * @param e
	 */
	public void mergeException(int errcode,Exception e){
		mergeException(e);
		this.errcode=errcode;
		this.put("errcode", errcode);
	}

	/**
	 * 把异常作为错误输出
	 * @param e
	 */
	public void mergeException(Exception e) {
		if(e instanceof SQLException) {
			errcode = 10801;
			errmsg = "数据库访问失败";
			Log log = LogFactory.getLog(this.getClass());
			log.error("数据库异常",e);
		}
		else if(e instanceof BadSqlGrammarException) {//spring转换得到的异常
			errcode = 10801;
			errmsg = "数据库访问失败";
			Log log = LogFactory.getLog(this.getClass());
			log.error("sql语法异常",e);
		}
		else if(e instanceof NullPointerException){
			errcode = 10801;
			errmsg = "内部访问失败";
			Log log = LogFactory.getLog(this.getClass());
			log.error("空指针错误",e);
		}
		else if(e instanceof ValidateException){
			ValidateException ve = (ValidateException) e;
			errcode = ve.getErrcode();
			if(errcode<100){
				
				errcode += baseerrcode;
			}
			errmsg = ve.getMessage();
			Log log = LogFactory.getLog(this.getClass());
			log.error("校验错误",e);
		}
		else if(e instanceof BusinessException){
			BusinessException be = (BusinessException) e;
			errcode=be.getErrcode();
			errmsg = be.getMessage();
			if(errcode<100){
				
				errcode += baseerrcode;
			}
		}
		else{
			errcode=10801;
			errmsg = e.getMessage();
		}
		this.put("errmsg", errmsg);
		this.put("errcode", errcode);
		
	}
	
	/**
	 * 设置错误码的大范围，它应该在最后进行设置
	 * @param base
	 */
	public void setBaseErrorCode(int base){
		this.baseerrcode = base;
		if(errcode!=0){
			errcode=base+errcode;
		}
		this.put("errcode", errcode);
	}
	
}
