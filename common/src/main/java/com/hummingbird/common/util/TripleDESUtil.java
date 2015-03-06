package com.hummingbird.common.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 3DES加密工具类
 * @author Lion
 *
 */
public class TripleDESUtil {
	
	// 定义加密算法，有DES、DESede(即3DES)、Blowfish
		private static final String Algorithm = "DESede";

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
				byteMi = getEncCode(byteMing, build3DesKey(strKey));
				strMi = base64en.encode(byteMi).replaceAll("\n", "").replaceAll("\r", "");
				System.out.println(String.format("对明文%s采用密钥%s进行加密，密码为%s", data,strKey,strMi));
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
				byteMing = decryptMode(byteMi, build3DesKey(strKey));
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
		 * 加密方法
		 * @param src 源数据的字节数组
		 * @param key 密钥字节数据
		 * @return
		 */
		public static byte[] getEncCode(byte[] src, byte[] key) {
			try {
				SecretKey deskey = new SecretKeySpec(key, Algorithm); // 生成密钥
				Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
				c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
				return c1.doFinal(src);
			} catch (java.security.NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} catch (javax.crypto.NoSuchPaddingException e2) {
				e2.printStackTrace();
			} catch (java.lang.Exception e3) {
				e3.printStackTrace();
			}
			return null;
		}

		/**
		 * 解密函数
		 * @param src 密文的字节数组
		 * @param key 密钥字节数据
		 * @return
		 */
		public static byte[] decryptMode(byte[] src, byte[] key) {
			try {
				SecretKey deskey = new SecretKeySpec(key, Algorithm);
				Cipher c1 = Cipher.getInstance(Algorithm);
				c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
				return c1.doFinal(src);
			} catch (java.security.NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} catch (javax.crypto.NoSuchPaddingException e2) {
				e2.printStackTrace();
			} catch (java.lang.Exception e3) {
				e3.printStackTrace();
			}
			return null;
		}

		/*
		 * 根据字符串生成密钥字节数组
		 * 
		 * @param keyStr 密钥字符串
		 * 
		 * @return
		 * 
		 * @throws UnsupportedEncodingException
		 */
		public static byte[] build3DesKey(String keyStr)
				throws UnsupportedEncodingException {
			byte[] key = new byte[24];
			byte[] temp = keyStr.getBytes("UTF-8");

			if (key.length > temp.length) {
				// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
				System.arraycopy(temp, 0, key, 0, temp.length);
			} else {
				// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
				System.arraycopy(temp, 0, key, 0, key.length);
			}
			return key;
		}
		
		public static void main(String[] args) {
			String strKey = "123qwe!@#4rf%TGhy67il9ij12481632";

			String str = "Happy New Year!新年快乐!";
			System.out.println("明文：" + str);
			
			String strEnc = encryptBased3Des(str, strKey);// 加密字符串,返回String的密文
			System.out.println("密文： " + strEnc);
			
			String strDes = decryptBased3Des(strEnc, strKey);// 把String 类型的密文解密
			System.out.println("解密： " + strDes);
	    }
}
