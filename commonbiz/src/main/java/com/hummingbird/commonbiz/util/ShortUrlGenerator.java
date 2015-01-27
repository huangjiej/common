/**
 * 
 * ShortUrlGenerator.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.commonbiz.util;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.EncodingUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.ValidateUtil;

/**
 * @author huangjiej_2
 * 2014年12月23日 下午11:35:42
 * 本类主要做为短链生成器
 */
public class ShortUrlGenerator {
	
	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(ShortUrlGenerator.class);

	/**
	 * 生成短链
	 * @param content
	 * @return
	 * @throws DataInvalidException
	 */
	public static String genShortUrl(String content) throws DataInvalidException{
		ValidateUtil.assertNull(content, "短链内容为空");
		//把内容md5后取16位的内容，并进行base64，得到短链
		String encrypt = Md5Util.Encrypt(content);
		//System.out.println("原内容:"+content);
		//System.out.println("md5(32位）:"+encrypt);
		String shortenc = encrypt.substring(8,24);
		//System.out.println("md5(16位）:"+shortenc);
		byte[] encodeBase64 = EncodingUtil.encodeBase64(EncodingUtil.hexStringToBytes(shortenc));
		String shorturl = new String(encodeBase64);
		shorturl=shorturl.replaceAll("=", "").replaceAll("/", "-").replaceAll("\\+", "_");
		//System.out.println("md5(base64）:"+shorturl);
		return shorturl;
	}
	
	public static void main(String[] args) throws DataInvalidException {
		
		String full = "18922260815"+"13912345678"+"263";
		System.out.println(genShortUrl(full));
		
		
	}
	
	
}
