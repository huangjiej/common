package com.hummingbird.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;

/**
 * 校验工具类
 * 
 * @author huangjiej_2 2014年6月3日 下午10:15:14
 */
public abstract class ValidateUtil {
	private static final Log log = LogFactory.getLog(ValidateUtil.class);

	/**
	 * 检查属性是否为空或无内容
	 * 
	 * @param value
	 * @param name
	 * @return true-检查通过，false- 
	 * @throws ValidateException
	 */
	public static void assertNull(Object value, String name) throws DataInvalidException
	{
		assertNull(value,name,null);
	}
	/**
	 * 检查属性是否为空或无内容
	 * 
	 * @param value
	 * @param name
	 * @return true-检查通过，false- 
	 * @throws ValidateException
	 */
	public static void assertNull(Object value, String msg,Integer errcode) throws DataInvalidException
	{
		if(errcode==null)
		{
			errcode=ValidateException.ERRCODE_NULLVALUE;
		}
		if (value==null||!StringUtils.hasText(value.toString())) {
			if(log.isDebugEnabled()){
				log.debug("参数错误，" + msg);
			}
			
			//throw new DataInvalidException(errcode, msg );
			throw ValidateException.ERROR_PARAM_NULL.cloneAndAppend(null, msg);
		}
	}
	
	/**
	 * 判断指定参数无内容
	 * @param str
	 * @param msg
	 * @throws DataInvalidException 
	 */
	public static void assertEmpty(String str,String msg) throws DataInvalidException{
		if(org.apache.commons.lang.StringUtils.isBlank(str)){
			throw ValidateException.ERROR_PARAM_NULL.cloneAndAppend(null, msg);
		}
	}

	/**
	 * 验证字段长度
	 * 
	 * @param val
	 * @param len
	 * @param name
	 * @throws ValidateException
	 */
	public static void validateValueLen(Object val, int len, String name)
			throws ValidateException {
		if (val != null && val.toString().length() > len) {
			throw new ValidateException(1000, "参数错误，" + name + "的值长度大于" + len);
		}
	}

	
	/**
	 * 按字典值排序输出
	 * @param values
	 * @return
	 */
	public static String sortbyValues(String ... values) {
		List<String> sortlist = new ArrayList<String>();
		for (int i = 0; i < values.length; i++) {
			String str = values[i];
			if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
				sortlist.add(str);
			}
		}
		Collections.sort(sortlist);
		StringBuilder sb1 = new StringBuilder();
		for (Iterator iterator = sortlist.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			sb1.append(str);
			
		}
		String output = sb1.toString();
		return output;
	}
	/**
	 * 按字典值排序输出
	 * @param values
	 * @return
	 */
	public static String sortbyValues(List<String> list){
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			if(org.apache.commons.lang.StringUtils.isBlank(str)){
				iterator.remove();
			}
		}
		
		Collections.sort(list);
		StringBuilder sb1 = new StringBuilder();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			sb1.append(str);
			
		}
		String output = sb1.toString();
		return output;
	}
	
	public static void main(String[] args) {
		List<String > sha1sortlist = new ArrayList<String>();
		String[] values = new String[]{"1","a",null,"2ab"};
		String verifyData = sortbyValues(values);
		System.out.println(verifyData);
		sha1sortlist.add("a");
		sha1sortlist.add("2ab");
		sha1sortlist.add(null);
		sha1sortlist.add("1");
		System.out.println(sortbyValues(sha1sortlist));
		System.out.println(org.apache.commons.lang.ObjectUtils.equals(null, null));
	}

	public static void validateMobile(String mobileNum) throws DataInvalidException {
		validateMobile(mobileNum,null);
		
	}
	public static void validateMobile(String mobileNum,Integer errcode) throws DataInvalidException {
		if(errcode==null){
			errcode = ValidateException.ERRCODE_MOBILE_INVALID;
		}
		if(StringUtils.isEmpty(mobileNum)||mobileNum.length()!=11){
			if(log.isDebugEnabled()){
				log.debug(String.format("手机号码有误，为空或不为11位,%s",mobileNum));
			}
			throw ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "手机号码");
//			throw new DataInvalidException(errcode,"手机号码不正确");
		}
	}
	
	/**
	 * 检查不相等
	 * @param checkvalue
	 * @param descvalue
	 * @param errmsg
	 * @param errcode
	 * @throws DataInvalidException 
	 */
	public static void assertNotEqual(Object checkvalue, Object descvalue,
			String errmsg) throws DataInvalidException{
		assertNotEqual(checkvalue,descvalue,errmsg,null);
	}

	/**
	 * 检查不相等
	 * @param checkvalue
	 * @param descvalue
	 * @param errmsg
	 * @param errcode
	 * @throws DataInvalidException 
	 */
	public static void assertNotEqual(Object checkvalue, Object descvalue,
			String errmsg,Integer errcode) throws DataInvalidException {
		if(errcode==null){
			errcode = 1000;
		}
		if(!org.apache.commons.lang.ObjectUtils.equals(checkvalue, descvalue)){
			throw new DataInvalidException(errcode,errmsg);
		}
		
	}
	
	/**
	 * 生成签名明文
	 * @param obj
	 * @return
	 * @throws DataInvalidException
	 */
	public static String getPaintText(Object obj) throws DataInvalidException{

		StringBuilder str = new StringBuilder();
		//空对象
		if (obj == null) {
			return "";
		}
		//基本对象
		if (obj instanceof String || obj instanceof Number
				|| obj instanceof Boolean || obj instanceof Character) {
			str.append(obj.toString());
		} else if (obj.getClass().isArray()) {
			//数组,处理内部,并排序
			Object[] objarr = (Object[]) obj;
			List<String> ptlist = new ArrayList<String>();
			for (int j = 0; j < objarr.length; j++) {
				Object object = objarr[j];
				ptlist.add(getPaintText(object));
			}
			str.append(sortbyValues(ptlist));
		} else if (obj instanceof Date) {
			Date date = (Date) obj;
			String time = DateUtil.formatCommonDate(date);
			str.append(time);
		} else if (obj instanceof Collection) {
			//数组,处理内部,并排序
			Collection lll = (Collection) obj;
			List<String> ptlist = new ArrayList<String>();
			for (Iterator iterator = lll.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				ptlist.add(getPaintText(object));
			}
			str.append(sortbyValues(ptlist));

		} else if (obj instanceof Map) {
			//数组,处理内部,并排序
			Map m = (Map) obj;
			Set entrySet = m.entrySet();
			List<String> ptlist = new ArrayList<String>();
			for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
				Map.Entry en = (Map.Entry) iterator.next();
				ptlist.add(getPaintText(en.getValue()));

			}
			str.append(sortbyValues(ptlist));

		} else {
			Class classType = obj.getClass();
			Field[] fileds = classType.getDeclaredFields();// 得到实体的所有属性
			List<String> ptlist = new ArrayList<String>();
			for (int i = 0; i < fileds.length; i++) {
				fileds[i].setAccessible(true);
				Object o;
				try {
					o = fileds[i].get(obj);
					ptlist.add(getPaintText(o));

				} catch (Exception e) {
					log.error("获取bean的字段进行生成明文出错",e);
					throw  ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"获取bean的字段进行生成明文出错");
				}

			}
			str.append(sortbyValues(ptlist));

		}
		return str.toString();
	
	}


	
}
