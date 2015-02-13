package com.hummingbird.common.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 3DES加密工具类
 * @author Lion
 *
 */
public class TripleDESUtil {
	
	private static final String Algorithm = "DESede"; // 定义加密算法
	
	/**
	 * 根据参数生成KEY
	 * 
	 * @param strKey 密钥字符串
	 * @return KEY
	 */
	public static Key getKey(String strKey) {
		Key key = null;
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DESede");
			_generator.init(new SecureRandom(strKey.getBytes()));
			key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			throw new RuntimeException("3DES获取密钥错误，错误信息：", e);
		}
		return key;
	}

	/**
	 * 加密String明文输入,String密文base64输出
	 * 
	 * @param data String明文
	 * @param strKey 密钥字符串
	 * @return String密文
	 */
	public static String encryptBased3Des(String data, String strKey) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = data.getBytes("UTF8");
			byteMi = getEncCode(byteMing, getKey(strKey));
			strMi = base64en.encode(byteMi).replaceAll("\n", "").replaceAll("\r", "");
		} catch (Exception e) {
			throw new RuntimeException("3DES加密错误，错误信息：", e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param data String密文
	 * @param strKey 密钥字符串
	 * @return String密文
	 */
	public static String decryptBased3Des(String data, String strKey) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(data);
			byteMing = getDesCode(byteMi, getKey(strKey));
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException("3DES解密错误，错误信息：", e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS byte[]明文
	 * @param key密钥
	 * @return byte[]密文
	 * @throws Exception 
	 */
	private static byte[] getEncCode(byte[] byteS, Key key) throws Exception {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(Algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw e;
		} finally {
			cipher = null;
		}
		return byteFina;
	}


	/**
	 * 解密以byte[]密文输入,byte[]明文输出
	 * 
	 * @param byteS byte[]明文
	 * @param key密钥
	 * @return byte[]密文
	 * @throws Exception 
	 */
	private static byte[] getDesCode(byte[] byteD, Key key) throws Exception {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance(Algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw e;
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	public static void main(String[] args) throws Exception {
		String strKey = "123qwe!@#4rf%TGhy67il9ij12481632";

		String str = "Happy New Year!新年快乐!";
		System.out.println("明文：" + str);
		
		String strEnc = encryptBased3Des(str, strKey);// 加密字符串,返回String的密文
		System.out.println("密文： " + strEnc);
		
		String strDes = decryptBased3Des(strEnc, strKey);// 把String 类型的密文解密
		System.out.println("解密： " + strDes);
	}
}
