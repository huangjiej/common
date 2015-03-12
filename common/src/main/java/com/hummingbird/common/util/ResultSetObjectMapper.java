/**
 * 
 * ResultSetObjectMapper.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author john huang 2015年3月6日 下午12:44:26 本类主要做为
 */
public class ResultSetObjectMapper<T> {

	/**
	 * 转成对象，以列名为属性名来做
	 * @param rs
	 * @param outputClass
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */
	public T mapRersultSetToObject(ResultSet rs, Class<T> outputClass) throws InstantiationException, IllegalAccessException, SQLException{
		
		if (rs != null) {
			T obj = outputClass.newInstance();
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int _iterator = 0; _iterator < rsmd
					.getColumnCount(); _iterator++) {
				// getting the SQL column name
				String columnName = rsmd
						.getColumnName(_iterator + 1);
				// reading the value of the SQL column
				Object columnValue = rs.getObject(_iterator + 1);
				
				try {
					PropertyUtils.setProperty(obj, columnName, columnValue);
				} catch (Exception e) {
					//出错不处理
				}
				
			}
			return obj;
		}
		return null;
	}
	
	
	/**
	 * 把结果转为列表
	 * @param rs
	 * @param outputClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> mapRersultSetToObjectList(ResultSet rs, Class<T> outputClass) {

		List<T> outputList =new ArrayList<T>();
		T obj;
		try {
			obj = mapRersultSetToObject(rs,outputClass);
			if(obj!=null){
				outputList.add(obj);
			}
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
//			log.error(String.format(""),e);
		}
		return outputList;

	}

}
