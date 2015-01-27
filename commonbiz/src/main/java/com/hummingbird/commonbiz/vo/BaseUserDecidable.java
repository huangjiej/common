package com.hummingbird.commonbiz.vo;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.ValidateUtil;

/**
 * app使用的认证基类
 * @author huangjiej_2
 * 2014年10月18日 上午9:36:53
 */
public class BaseUserDecidable extends BaseUserToken implements Decidable {
	
	private String timeStamp;	
	private String nonce;	
	private String signature;
	private String appKey;
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString() {
		return "BaseUserDecidable [timeStamp=" + timeStamp + ", nonce=" + nonce
				+ ", signature=" + signature + ", appKey=" + appKey
				+ ", getToken()=" + getToken() + ", getAppId()=" + getAppId()
				+ ", getMobileNum()=" + getMobileNum() + "]";
	}
	@Override
	public int getType() {
		return 0;
	}
	@Override
	public void setOtherParam(Map map) {
		try {
			BeanUtils.copyProperties(this, map);
		} catch (IllegalAccessException e) {
			//业务处理失败时，可以不管，因为它最终会导致认证不通过，不过需要在日志中进行警告
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public boolean isAuthed() {
		String text = getPlaintext();
		String encrypt = Md5Util.Encrypt(text);
		System.out.println("baseuserDecide="+text+":"+encrypt);
		return encrypt.equals(getSignature());
	}
	
	/**
	 * 获取待验签明文数据
	 * @return
	 */
	public String getPlaintext(){
		String token = getToken();
		if(StringUtils.isEmpty(token)){
			token="";
		}
		String text = ValidateUtil.sortbyValues(getAppId(),appKey,nonce,timeStamp,token);
		
		return text;
		
	}
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}	
	
}
