/**
 * 
 * JdbcUtil.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author john huang
 * 2015年8月5日 下午8:31:08
 * 本类主要做为jdbc工具类
 */
public class JdbcUtil {
	
	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(JdbcUtil.class);

	public static JdbcTemplate getJDBC(){
		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		return jdbc;
	}
	
	
	/**
	 * 获取第一条记录
	 * @param sql
	 * @param args
	 * @return
	 */
	public static Map<String,Object> queryForMap(String sql,Object ... args){
		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		if (log.isDebugEnabled()) {
			log.debug(String.format("查询sql为 %s",sql));
		}
		List<Map<String, Object>> queryForList = jdbc.queryForList(sql,args);
		if(!queryForList.isEmpty())
		{
			return queryForList.get(0);
		}
		return null;
	}
	
	/**
	 * 查询一条记录
	 * @param sql
	 * @return
	 */
	public static Map<String,Object> queryForMap(String sql){
		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		if (log.isDebugEnabled()) {
			log.debug(String.format("查询sql为 %s",sql));
		}
		List<Map<String, Object>> queryForList = jdbc.queryForList(sql);
		if(!queryForList.isEmpty())
		{
			return queryForList.get(0);
		}
		return null;
	}
	
	
	/**
	 * 获取sum,count等聚合记录
	 * @param sql
	 * @param args
	 * @return
	 */
	public static Long queryForLong(String sql,Object ... args){
		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		if (log.isDebugEnabled()) {
			log.debug(String.format("查询sql为 %s",sql));
		}
		Long queryForList = jdbc.queryForObject(sql,args,Long.class);
		return queryForList;
	}
	
	/**
	 * 获取sum,count等聚合记录
	 * @param sql
	 * @return
	 */
	public static Long queryForLong(String sql){
		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		if (log.isDebugEnabled()) {
			log.debug(String.format("查询sql为 %s",sql));
		}
		Long queryForList = jdbc.queryForObject(sql,Long.class);
		return queryForList;
	}


	/**
	 * 查询列表
	 * @param sql
	 * @return
	 */
	public static List<Map<String, Object>> queryForList(String sql) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("查询sql为 %s",sql));
		}
		return getJDBC().queryForList(sql);
	}
	
	/**
	 * 查询列表
	 * @param sql
	 * @return
	 */
	public static List<Map<String, Object>> queryForList(String sql,Object ... args) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("查询sql为 %s",sql));
		}
		if (log.isTraceEnabled()) {
			log.trace(String.format("参数为 %s",args));
		}
		return getJDBC().queryForList(sql,args);
	}
	
	
	
}
