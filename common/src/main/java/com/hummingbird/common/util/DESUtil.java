package com.hummingbird.common.util;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class DESUtil {
	private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128,
			-65 };

	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */
	public static String encryptBasedDes(String data) {
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 加密，并把字节数组编码成字符串
			encryptedData = new sun.misc.BASE64Encoder().encode(cipher
					.doFinal(data.getBytes()));
		} catch (Exception e) {
			// log.error("加密错误，错误信息：", e);
			throw new RuntimeException("加密错误，错误信息："+e.getMessage(), e);
		}
		return encryptedData;
	}
	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */
	public static String encryptDes(String data,String DESkey) {
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DESkey.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 加密，并把字节数组编码成字符串
			encryptedData = new sun.misc.BASE64Encoder().encode(cipher
					.doFinal(data.getBytes()));
		} catch (Exception e) {
			// log.error("加密错误，错误信息：", e);
			throw new RuntimeException("加密错误，错误信息："+e.getMessage(), e);
		}
		return encryptedData;
	}
	
	/**
	 * 使用cbc,PKCS5Padding填充模式进行加密,密钥大于等于8位,只取8位,初始向量使用密钥倒置后8个byte,此方法能与php兼容
	 * @param data
	 * @param DESkey
	 * @return
	 */
	public static String encryptDESwithCBC(String data,String DESkey) {
		if(DESkey==null){
			throw new RuntimeException("加密错误，密钥为空");
		}
		else{
			DESkey=DESkey.trim();
			if(DESkey.length()<8){
				throw new RuntimeException("加密错误，密钥长度不足8位");
			}
			else{
				DESkey = DESkey.substring(0,8);
			}
		}
		byte[] bytes;
		try {
			bytes = StringUtils.reverse(DESkey).getBytes("utf8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("加密错误，字符编码转换失败："+e.getMessage(), e);
		}
		return encryptDes(data,DESkey,"DES/CBC/PKCS5Padding",ArrayUtils.subarray(bytes, 0, 8));
	}
	
	/**
	 * 使用cbc,PKCS5Padding填充模式进行解密,初始向量使用密钥倒置后8个byte,此方法能与php兼容
	 * 
	 * @param cryptData   加密数据
	 * @return 解密后的数据
	 */
	public static String decodeDESwithCBC(String cryptData,String DESkey) {
		byte[] bytes;
		try {
			bytes = StringUtils.reverse(DESkey).getBytes("utf8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("解密错误，字符编码转换失败："+e.getMessage(), e);
		}
		return decodeDES(cryptData,DESkey,"DES/CBC/PKCS5Padding",ArrayUtils.subarray(bytes, 0, 8));
	}
	
	
	
	/**
	 * 数据加密，算法（DES）,通过transformer 指定具体的算法目前有
	 * DES/CBC/NoPadding (56)
	 * DES/CBC/PKCS5Padding (56)
	 * DES/ECB/NoPadding (56)
	 * DES/ECB/PKCS5Padding (56)
	 * CBC模式需要传向量
	 * @param cryptData    数据
	 * @param DESkey   密钥
	 * @param transformer 具体的算法
	 * @param iv  向量
	 * @return 加密后的数据
	 */
	public static String encryptDes(String data,String DESkey,String transformer,byte[] iv) {
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DESkey.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance(transformer);
			if(null==(iv)){
				cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			}
			else{
				
				IvParameterSpec iv2 = new IvParameterSpec(StringUtils.reverse(DESkey).getBytes("utf8"),0,8);
				cipher.init(Cipher.ENCRYPT_MODE, key, iv2);
			}
			// 加密，并把字节数组编码成字符串
			encryptedData = new sun.misc.BASE64Encoder().encode(cipher
					.doFinal(data.getBytes()));
		} catch (Exception e) {
			// log.error("加密错误，错误信息：", e);
			throw new RuntimeException("加密错误，错误信息："+e.getMessage(), e);
		}
		return encryptedData;
	}

	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static String decryptBasedDes(String cryptData) {
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(
					cipher.doFinal(new sun.misc.BASE64Decoder()
							.decodeBuffer(cryptData)));
		} catch (Exception e) {
			// log.error("解密错误，错误信息：", e);
			throw new RuntimeException("解密错误，错误信息："+e.getMessage(), e);
		}
		return decryptedData;
	}
	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 * @throws UnsupportedEncodingException 
	 */
	public static String decodeDES(String cryptData,String DESkey) throws UnsupportedEncodingException {
		String decryptedData = null;
		
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DESkey.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(
					cipher.doFinal(new sun.misc.BASE64Decoder()
							.decodeBuffer(cryptData)));
		} catch (Exception e) {
			// log.error("解密错误，错误信息：", e);
			throw new RuntimeException("解密错误，错误信息："+e.getMessage(), e);
		}
		return decryptedData;
	}
	/**
	 * 数据解密，算法（DES）,通过transformer 指定具体的算法
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 * @throws UnsupportedEncodingException 
	 */
	public static String decodeDES(String cryptData,String DESkey,String transformer) throws UnsupportedEncodingException {
		String decryptedData = null;
		
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DESkey.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance(transformer);
//			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			IvParameterSpec iv2 = new IvParameterSpec(StringUtils.reverse(DESkey).getBytes("utf8"),0,8);
			cipher.init(Cipher.DECRYPT_MODE, key, iv2);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(
					cipher.doFinal(new sun.misc.BASE64Decoder()
					.decodeBuffer(cryptData)));
		} catch (Exception e) {
			// log.error("解密错误，错误信息：", e);
			throw new RuntimeException("解密错误，错误信息："+e.getMessage(), e);
		}
		return decryptedData;
	}
	
	/**
	 * 数据解密，算法（DES）,通过transformer 指定具体的算法目前有
	 * DES/CBC/NoPadding (56)
	 * DES/CBC/PKCS5Padding (56)
	 * DES/ECB/NoPadding (56)
	 * DES/ECB/PKCS5Padding (56)
	 * CBC模式需要传向量
	 * @param cryptData    加密数据
	 * @param DESkey   密钥
	 * @param transformer 具体的算法
	 * @param iv  向量
	 * @return 解密后的数据
	 */
	public static String decodeDES(String cryptData,String DESkey,String transformer,byte[] iv)  {
		String decryptedData = null;
		
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DESkey.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance(transformer);
			if(null == (iv)){
				cipher.init(Cipher.DECRYPT_MODE, key, sr);
			}
			else{
				
				IvParameterSpec iv2 = new IvParameterSpec(iv,0,8);
				cipher.init(Cipher.DECRYPT_MODE, key, iv2);
			}
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(
					cipher.doFinal(new sun.misc.BASE64Decoder()
					.decodeBuffer(cryptData)));
		} catch (Exception e) {
			// log.error("解密错误，错误信息：", e);
			throw new RuntimeException("解密错误，错误信息："+e.getMessage(), e);
		}
		return decryptedData;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		System.out.println(DESUtil.encryptDes("223344", "13e998492ad888e695a431c68ac28178"));
//		System.out.println(DESUtil.decodeDES("vxJUkQCK/v4=", "13e998492ad888e695a431c68ac28178"));
//		System.out.println(DESUtil.encryptBasedDes("um4016091213&#$!"));
		System.out.println(DESUtil.encryptDes("223344", "13e998492ad888e695a431c68ac28178"));
		System.out.println(DESUtil.encryptDes("4034224aa&", "email.psw"));
		System.out.println(DESUtil.decodeDES("AJ3QfM03nz0=", "13e998492ad888e695a431c68ac28178"));
		
		
	}
	
}
