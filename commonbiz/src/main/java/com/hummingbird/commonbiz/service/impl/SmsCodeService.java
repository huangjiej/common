/**
 * 
 */
package com.hummingbird.commonbiz.service.impl;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.commonbiz.entity.SmsCode;
import com.hummingbird.commonbiz.exception.TokenException;
import com.hummingbird.commonbiz.mapper.SmsCodeMapper;
import com.hummingbird.commonbiz.service.ISmsCodeService;
import com.hummingbird.commonbiz.util.AuthCodeUtil;
import com.hummingbird.commonbiz.vo.BaseUserToken;
import com.hummingbird.commonbiz.vo.UserToken;

/**
 * 短信验证码相关服务类
 * @author huangjiej_2
 * 2014年11月12日 上午10:29:30
 */
@Service
public class SmsCodeService implements ISmsCodeService {

	Log log = LogFactory.getLog(getClass());
	
	@Autowired(required = true)
	private SmsCodeMapper smsMapper;
	
	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.service.ISmsCodeService#validateToken(com.hummingbird.commonbiz.vo.UserToken)
	 */
	@Override
	public boolean validateToken(String appId, String mobileNum,String authcode) throws TokenException {
		boolean authCodeSuccess = AuthCodeUtil.validateAuthCode(appId,mobileNum,authcode);
		return authCodeSuccess;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.service.ISmsCodeService#createToken(java.lang.String, java.lang.String)
	 */
	@Override
	public UserToken createToken(String appId, String mobileNum) {
		//检验手机,工作可能在之前已经做了
		return createToken(appId,mobileNum,4);
	}
	
	@Override
	public UserToken createToken(String appId, String mobileNum,int len) {
		//检验手机,工作可能在之前已经做了
		SmsCode smscode=new SmsCode();
		smscode.setAppid(appId);
		smscode.setMobilenum(mobileNum);
		SmsCode dbsmscode=smsMapper.getAuthCode(smscode);
		String authCode=null;
		boolean needstore = true;
		if(dbsmscode!=null)
		{
			if(dbsmscode.getSendtime().getTime()+dbsmscode.getExpirein()*1000>System.currentTimeMillis())
			{
				authCode = dbsmscode.getSmscode();
				needstore=false;
				
			}
			else{
				//删除数据库中的验证码
				authCode=null;
				smsMapper.deleteAuthCode(dbsmscode);
			}
		}
		if(authCode==null)
		{
			authCode = AuthCodeUtil.getAuthCode(appId,mobileNum,len);
			System.out.println(authCode);
		}
		//String content = AuthCodeUtil.getAuthCodeByTemplate(authCode);
		if(needstore){
			smscode.setSendtime(new Date());
			String timeout = new PropertiesUtil().getProperty("sms.authcode.timeout");
			smscode.setExpirein(NumberUtils.toInt(timeout, 60));
			smscode.setSmscode(authCode);
			smsMapper.insert(smscode);
		}
		else{
			smscode = dbsmscode;
		}
		return new BaseUserToken(appId, mobileNum, smscode.getSmscode(),smscode.getExpirein());
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.service.ISmsCodeService#queryToken()
	 */
	@Override
	public UserToken queryToken(String appId, String mobileNum) {
		SmsCode smscode=new SmsCode();
		smscode.setAppid(appId);
		smscode.setMobilenum(mobileNum);
		SmsCode dbsmscode=smsMapper.getAuthCode(smscode);
		return dbsmscode==null?null:new BaseUserToken(appId, mobileNum, dbsmscode.getSmscode(),dbsmscode.getExpirein());
	}

}
