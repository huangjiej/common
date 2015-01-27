package com.hummingbird.common.util;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.exception.SignatureException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.Md5Util;

/**
 * 签名工具类
 * @author huangjiej_2
 * 2014年11月11日 上午12:29:06
 */
public class SignatureUtil {

	/**
	 * md5加密
	 */
	public static int SIGNATURE_TYPE_MD5=0;
	
	
	/**
	 * 校验签名
	 * @param signaturetype 签名方式
	 * @param values 内容
	 * @return
	 */
	public static boolean validateSignature(String signature,int signaturetype,String ... values ) throws SignatureException{ 
		//字典值排序
		if(values.length==0){
			throw ValidateException.ERROR_SIGNATURE_MD5.clone(null,"验证签名失败，明文内容为空");
//			throw new SignatureException("验证签名，明文内容为空");
		}
		if(StringUtils.isBlank(signature)){
			throw ValidateException.ERROR_SIGNATURE_MD5.clone(null,"验证签名失败，签名为空");
//			throw new SignatureException("验证签名，签名为空");
		}
		
		ArrayBuilder<String> forverify = new ArrayBuilder<String>();
		for (int i = 0; i < values.length; i++) {
			String str = values[i];
			if(str==null){
				forverify.add("");
			}
			else{
				forverify.add(str);
			}
		}
		//对内容进行字典排序
		//暂时处理
		String mingwen = ValidateUtil.sortbyValues(forverify.internallist);
		
		if(signaturetype==SIGNATURE_TYPE_MD5){
			String encrypt = Md5Util.Encrypt(mingwen);
			System.out.println("验证签名，明文为"+mingwen+",密文为 "+encrypt);
			return signature.equals(encrypt);
		}
		return false;
	}
	
	/**
	 * 基于证书（公钥）校验签名
	 * @param signaturetype 签名方式
	 * @param values 内容
	 * @return
	 */
	public static boolean verifySignatureByPublicKey(String signature,String publicKey,String ... values ) throws SignatureException{ 
		return false;
	}
	
	
}
