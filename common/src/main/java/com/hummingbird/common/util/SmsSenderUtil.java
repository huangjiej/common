/**
 * 
 */
package com.hummingbird.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.http.HttpRequester;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.common.vo.ResultModel;

/**
 * @author huangjiej_2
 * 2014年11月2日 下午8:24:45
 */
public class SmsSenderUtil {
	
	
	private static final Log log = LogFactory.getLog(SmsSenderUtil.class);

	
	
	
	
	/**
	 * 按模板发送短信
	 * @param mobileNum
	 * @param templateName
	 * @param param
	 * @throws ValidateException
	 */
	public static void sendSms(String mobileNum,String templateName,DefaultKeyValue ... param) throws ValidateException{
			
		String template = getContentByTemplate(templateName, param);
		sendSms(mobileNum,template);	
	}


	/**
	 * @param templateName
	 * @param param
	 * @return
	 * @throws ValidateException
	 */
	public static String getContentByTemplate(String templateName,
			DefaultKeyValue... param) throws ValidateException {
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		String template = propertiesUtil.getProperty(templateName);
		if(StringUtils.isEmpty(template)){
			throw new ValidateException("获取模板出错");
		}
		for (int i = 0; i < param.length; i++) {
			DefaultKeyValue en = param[i];
			Object key = en.getKey();
			Object value = en.getValue();
			template=template.replaceAll("\\$\\{"+key+"\\}", ObjectUtils.toString(value));
		}
		return template;
	}
	
	
	/**
	 * 按模板发送短信
	 * @param mobileNum
	 * @param templateName
	 * @param param
	 * @return 短信内容
	 * @throws ValidateException
	 */
	public static String sendSms(String mobileNum,String templateName,Map param) throws ValidateException{
		String template = getContentByTemplate(templateName, param);
		sendSms(mobileNum,template);
		return template;
	}

	/**
	 * @param templateName
	 * @param param
	 * @return
	 * @throws ValidateException
	 */
	public static String getContentByTemplate(String templateName, Map param)
			throws ValidateException {
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		String template = propertiesUtil.getProperty(templateName);
		if(StringUtils.isEmpty(template)){
			throw new ValidateException("获取模板出错");
		}
		for (Iterator iterator = param.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry en = (Map.Entry) iterator.next();
			Object key = en.getKey();
			Object value = en.getValue();
			template=template.replaceAll("\\$\\{"+key+"\\}", ObjectUtils.toString(value));
		}
		return template;
	}
	
	/**
	 * 发送短信
	 * @param mobileNum
	 * @param content
	 * @throws ValidateException
	 */
	public static void sendSms(String mobileNum,String content)throws ValidateException{
		if(log.isDebugEnabled())
		{
			log.debug("发送短信开始,mobile="+mobileNum+",content="+content);
		}
		if(StringUtils.isEmpty(mobileNum)||mobileNum.length()!=11){
//			if(log.isDebugEnabled()){
//				log.debug(String.format("手机号码有误，为空或不为11位,%s",getsmsvo));
//			}
//			rm.setErr(1014, "手机号码有误");
//			return rm;
			throw new ValidateException(1001,String.format("手机号码有误，为空或不为11位,%s",mobileNum));
		}
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		final String smsurl = propertiesUtil.getProperty("smsWS");
		String user = propertiesUtil.getProperty("user");
		String password = propertiesUtil.getProperty("password");
		final Map smssend=new HashMap();
		smssend.put("user",user);
		smssend.put("password",password);
		smssend.put("mobileNum",mobileNum);
		smssend.put("content",content);
		
		final ResultModel rm = new ResultModel();
		new Thread(){
			
			public void run() {
				try{
					rm.setErrmsg("短信发送成功");
					String result = new HttpRequester().postRequest(smsurl, new JSONObject(smssend).toString());
					if(log.isDebugEnabled())
					{
						log.debug("发送短信,返回结果为:"+result);
					}
					JSONObject jsonObject = new JSONObject(result);
					String wsresult = jsonObject.optString("wsresult");
					if(NumberUtils.toLong(wsresult,-1L)<0){
						log.error("结果报告短信发送失败:"+result);
						rm.setErr(1002, "短信发送失败");
					}
					if(log.isDebugEnabled())
					{
						log.debug("短信发送成功");
					}
					
				}catch(Exception e){
					log.error("短信发送出错:",e);
					rm.setErr(1002, "短信发送失败");
				}
			};
		}.start();
		
		if(log.isDebugEnabled())
		{
			log.debug("发送短信完成");
		}
		
	}
	
	
	
}
