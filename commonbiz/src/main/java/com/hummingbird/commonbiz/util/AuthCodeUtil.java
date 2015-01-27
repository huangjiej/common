/**
 * 
 */
package com.hummingbird.commonbiz.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.commonbiz.entity.SmsCode;
import com.hummingbird.commonbiz.mapper.SmsCodeMapper;

/**
 * 验证码工具类
 * @author huangjiej_2
 * 2014年10月18日 下午8:51:18
 */
public abstract class AuthCodeUtil {
	
	static Log log = LogFactory.getLog(AuthCodeUtil.class); 

	static Map<String, TimeLimitCode> authcodeMap = new HashMap<String, TimeLimitCode>();
	
	static String templateName = "SMS_CODE_TEMPLATE";
	
	/**
	 * 获取验证码
	 * @param appId
	 * @param mobileNum
	 * @return
	 */
	public static String getAuthCode(String appId,String mobileNum){
		String id =appId+mobileNum;
		TimeLimitCode sms_code = authcodeMap.get(id);
//		if(sms_code==null){
//			sms_code=new TimeLimitCode();
//			authcodeMap.put(id,sms_code);
//		}
//		else if(sms_code.isTimeout()){
//			authcodeMap.remove(id);
			sms_code=new TimeLimitCode();
//			authcodeMap.put(id,sms_code);
//		}
		return sms_code.getAuthCode();
	}
	
	/**
	 * 获取验证码
	 * @param appId
	 * @param mobileNum
	 * @return
	 */
	public static String getAuthCode(String appId,String mobileNum,int len){
		String id =appId+mobileNum;
		TimeLimitCode sms_code = authcodeMap.get(id);
//		if(sms_code==null){
//			sms_code=new TimeLimitCode();
//			authcodeMap.put(id,sms_code);
//		}
//		else if(sms_code.isTimeout()){
//			authcodeMap.remove(id);
		sms_code=new TimeLimitCode(len);
//			authcodeMap.put(id,sms_code);
//		}
		return sms_code.getAuthCode();
	}
	
	/**
	 * 按模板加载
	 * @param appId
	 * @param mobileNum
	 * @return
	 */
	public static String getAuthCodeByTemplate(String appId,String mobileNum,String template_name)
	{
		String authCode = getAuthCode(appId,mobileNum);
		return getAuthCodeByTemplate(authCode,template_name);
	}
	
	/**
	 * 按模板生成验证码信息
	 * @param appId
	 * @param mobileNum
	 * @return
	 */
	public static String getAuthCodeByTemplate(String authCode,String template_name)
	{
//		String authCode = getAuthCode(appId,mobileNum);
		if(StringUtils.isBlank(template_name)){
			template_name = templateName;
		}
		String template = new PropertiesUtil().getProperty(template_name);
		String templatewithauthcode = template.replaceAll("\\$\\{authCode\\}", authCode);
		return templatewithauthcode;
	}
	
	/**
	 * 校验验证码
	 * @param appId
	 * @param mobileNum
	 * @param authCode
	 * @return
	 */
	public static boolean validateAuthCode(String appId,String mobileNum,String authCode){
		
		SmsCodeMapper smsmapper = SpringBeanUtil.getInstance().getBean(SmsCodeMapper.class);
		SmsCode query = new SmsCode();
		query.setAppid(appId);
		query.setMobilenum(mobileNum);
		SmsCode code = smsmapper.getAuthCode(query);
		if(log.isTraceEnabled())
		{
			log.trace("手机验证码信息是："+code);;
		}
		if(code!=null&&code.getSmscode().equals(authCode)&&(code.getSendtime().getTime()+code.getExpirein()*1000)>System.currentTimeMillis()){
			//删除验证码
			smsmapper.deleteAuthCode(query);
			return true;
		}
//		String authCode2 = getAuthCode(appId,mobileNum);
//		System.out.println("生成的验证码为"+authCode2);
//		return authCode2.equals(authCode);
		return false;
	}
	
	/**
	 * 限时验证码
	 * @author huangjiej_2
	 * 2014年10月18日 下午9:04:27
	 */
	public static class TimeLimitCode{
		
		int lastsec = 30*60*1000;
		
		
		String rawauthCode = new DecimalFormat("0000000000").format(Math.random()*10000000000L);
		String authCode;
		long genTime=System.currentTimeMillis();
		
		public TimeLimitCode(int len) {
			authCode = genAuthCode(len);
		}
		
		public TimeLimitCode() {
			this(4);
		}
		
		

		public String getAuthCode() {
			return authCode;
		}
		
		public String genAuthCode(int len){
			if(len<=0||len>10){
				System.out.println("请求的验证长度不正确，生成默认长度的验证码");
				len = 4;
			}
			return rawauthCode.substring(0,len);
		}
		
		/**
		 * 是否超时
		 * @return
		 */
		public boolean isTimeout() {
			return System.currentTimeMillis()-lastsec-genTime>0;
		}
		
		public boolean equals(Object obj){
			if(obj==null) return false;
			if (obj instanceof TimeLimitCode) {
				TimeLimitCode newtlc = (TimeLimitCode) obj;
				return newtlc.getAuthCode().equals(this.getAuthCode());
				
			}
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(AuthCodeUtil.getAuthCode("1", "1"));
		System.out.println(AuthCodeUtil.getAuthCode("1", "1",7));
		System.out.println(AuthCodeUtil.getAuthCode("1", "1",10));
	}
	 
}
