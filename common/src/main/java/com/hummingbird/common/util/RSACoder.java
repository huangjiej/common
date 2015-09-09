package com.hummingbird.common.util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
    
/**  
* RSA非对称加密算法安全编码组件  
* @author Administrator  
*  
*/    
public abstract class RSACoder {    
	
	private static final Log log = LogFactory.getLog(RSACoder.class);
   //非对称加密密钥算法    
   public static final String KEY_ALGORITHM="RSA";    
   //数字签名 签名/验证算法    
   public static final String SIGNATURE_ALGORRITHM_SHA1="SHA1withRSA";  
   
   //数字签名 签名/验证算法    
   public static final String SIGNATURE_ALGORRITHM_MD5="MD5withRSA"; 
   //公钥    
   private static final String PUBLIC_KEY="RSAPublicKey";    
   //私钥    
   private static final String PRIVATE_KEY="RSAPrivateKey";    
   //RSA密钥长度,默认为1024,密钥长度必须是64的倍数,范围在512~65526位之间    
   private static final int KEY_SIZE = 512;    
   /**  
    * 私钥解密  
    * @param data 待解密数据  
    * @param key 私钥  
    * @return byte[] 解密数据  
    * @throws Exception  
    */    
   public static byte[] decryptByPrivateKey(byte[] data,byte[]key) throws Exception    
   {    
   //取得私钥    
   PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);    
   KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);    
   //生成私钥    
   PrivateKey privateKey=keyFactory.generatePrivate(pkcs8KeySpec);    
   //对数据解密    
   Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());    
   cipher.init(Cipher.DECRYPT_MODE, privateKey);    
   return cipher.doFinal(data);    
   }    
   /**  
    * 私钥解密  
    * @param data 待解密数据  
    * @param key 私钥  
    * @return byte[] 解密数据  
    * @throws Exception  
    */    
   public static byte[] decryptByPrivateKey(byte[] data,String privateKey) throws Exception    
   {    
   return decryptByPrivateKey(data,getKey(privateKey));    
   }    
   /**  
    * 公钥解密  
    * @param data 待解密数据  
    * @param key 公钥  
    * @return byte[] 解密数据  
    * @throws Exception  
    */    
   public static byte[] decryptByPublicKey(byte[] data,byte[] key) throws Exception    
   {    
   //取得公钥    
   X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);   
   

   KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);    
   //生成公钥    
   PublicKey publicKey=keyFactory.generatePublic(x509KeySpec);    
   //对数据解密    
   Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());    
   cipher.init(Cipher.DECRYPT_MODE, publicKey);    
   return cipher.doFinal(data);    
   }    
   /**  
    * 公钥解密  
    * @param data 待解密数据  
    * @param key 公钥  
    * @return byte[] 解密数据  
    * @throws Exception  
    */    
   public static byte[] decryptByPublicKey(byte[] data,String publicKey) throws Exception    
   {    
   return decryptByPublicKey(data,getKey(publicKey));    
   }    
   /**  
    * 公钥加密  
    * @param data 待加密数据  
    * @param key 公钥  
    * @return byte[] 加密数据  
    * @throws Exception  
    */    
   public static byte[] encryptByPublicKey(byte[] data,byte[] key) throws Exception    
   {    
   //取得公钥    
   X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);    
   KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);    
   PublicKey publicKey=keyFactory.generatePublic(x509KeySpec);    
  //对数据加密    
   Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());    
   cipher.init(Cipher.ENCRYPT_MODE, publicKey);    
   return cipher.doFinal(data);    
   }    
   /**  
    * 公钥加密  
    * @param data 待加密数据  
    * @param key 公钥  
    * @return byte[] 加密数据  
    * @throws Exception  
    */    
   public static byte[] encryptByPublicKey(byte[] data,String publicKey) throws Exception    
   {    
	   return encryptByPublicKey(data,getKey(publicKey));    
   }    
   /**  
    * 私钥加密  
    * @param data 待加密数据  
    * @param key 私钥  
    * @return byte[] 加密数据  
    * @throws Exception  
    */    
   public static byte[] encryptByPrivateKey(byte[] data,byte[] key) throws Exception    
   {    
   //取得私钥    
   PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);    
   KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);    
   //生成私钥    
   PrivateKey privateKey=keyFactory.generatePrivate(pkcs8KeySpec);    
   //对数据加密    
   Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());    
   cipher.init(Cipher.ENCRYPT_MODE, privateKey);    
   return cipher.doFinal(data);    
   }    
   /**  
    * 私钥加密  
    * @param data 待加密数据  
    * @param key 私钥  
    * @return byte[] 加密数据  
    * @throws Exception  
    */    
   public static byte[] encryptByPrivateKey(byte[] data,String key) throws Exception    
   {    
   return encryptByPrivateKey(data,getKey(key));    
   }    
   /**  
    * 取得私钥  
    * @param keyMap 密钥Map  
    * @return byte[] 私钥  
    * @throws Exception  
    */    
   public static byte[] getPrivateKey(Map<String,Object> keyMap) throws Exception    
   {    
   Key key=(Key)keyMap.get(PRIVATE_KEY);    
   return key.getEncoded();    
   }    
   /**  
    * 取得公钥  
    * @param keyMap 密钥Map  
    * @return byte[] 公钥  
    * @throws Exception  
    */    
   public static byte[] getPublicKey(Map<String,Object> keyMap) throws Exception    
   {    
   Key key=(Key)keyMap.get(PUBLIC_KEY);    
   return key.getEncoded();    
   }    
   /**  
    * 初始化密钥  
    * @return 密钥Map  
    * @throws Exception  
    */    
   public static Map<String,Object> initKey() throws Exception
   {    
   //实例化实钥对生成器    
   KeyPairGenerator keyPairGen=KeyPairGenerator.getInstance(KEY_ALGORITHM);    
   //初始化密钥对生成器    
   keyPairGen.initialize(KEY_SIZE);    
   //生成密钥对    
   KeyPair keyPair=keyPairGen.generateKeyPair();    
   //公钥    
   RSAPublicKey publicKey=(RSAPublicKey) keyPair.getPublic();    
   //私钥    
   RSAPrivateKey privateKey=(RSAPrivateKey) keyPair.getPrivate();    
   //封装密钥    
   Map<String,Object> keyMap=new HashMap<String,Object>(2);    
   keyMap.put(PUBLIC_KEY, publicKey);    
   keyMap.put(PRIVATE_KEY, privateKey);    
   return keyMap;    
   }    
   /**  
    * 签名  
    * @param data 待签名数据  
    * @param privateKey 私钥  
    * @return byte[] 数字签名  
    * @throws Exception  
    */    
   public static byte[] sign(byte[] data,byte[] privateKey) throws Exception    
   {    
   //转接私钥材料    
   PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(privateKey);    
   //实例化密钥工厂    
   KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);    
   //取私钥对象    
   PrivateKey priKey=keyFactory.generatePrivate(pkcs8KeySpec);    
   //实例化Signature    
   Signature signature=Signature.getInstance(SIGNATURE_ALGORRITHM_SHA1);    
   //初始化Signature    
   signature.initSign(priKey);    
   //更新    
   signature.update(data);    
   //签名    
   return signature.sign();    
   }    
   
   /**  
    * 签名  
    * @param data 待签名数据  
    * @param privateKey 私钥  
    * @param algorrithm 签名算法
    * @return byte[] 数字签名  
    * @throws Exception  
    */    
   public static byte[] sign(byte[] data,byte[] privateKey,String algorrithm) throws Exception    
   {    
   //转接私钥材料    
   PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(privateKey);    
   //实例化密钥工厂    
   KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);    
   //取私钥对象    
   PrivateKey priKey=keyFactory.generatePrivate(pkcs8KeySpec);    
   //实例化Signature    
   Signature signature=Signature.getInstance(algorrithm);    
   //初始化Signature    
   signature.initSign(priKey);    
   //更新    
   signature.update(data);    
   //签名    
   return signature.sign();    
   }    
   
   
   /**  
    * 公钥校验  
    * @param data 待校验数据  
    * @param publicKey 公钥  
    * @param sign 数字签名  
    * @return  
    * @throws Exception  
    */    
   public static boolean verify(byte[] data,byte[] publicKey,byte[] sign) throws Exception    
   {    
   //转接公钥材料    
   X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(publicKey);
   //实例化密钥工厂    
   KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);    
   //生成公钥    
   PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);    
   //实例化Signature    
   Signature signature=Signature.getInstance(SIGNATURE_ALGORRITHM_SHA1);    
   //初始化Signature    
   signature.initVerify(pubKey);    
   //更新    
   signature.update(data);    
   //验证    
   return signature.verify(sign);    
   }    
   /**  
    * 私钥签名  
    * @param data 待签名数据  
    * @param privateKey 私钥  
    * @return String 十六进制签名字符串  
    * @throws Exception  
    */    
   public static String sign(byte[] data,String privateKey) throws Exception    
   {    
   byte[] sign=sign(data,getKey(privateKey));    
   return new String(Hex.encodeHex(sign));    
   }    
   
   /**  
    * 私钥签名  
    * @param data 待签名数据  
    * @param privateKey 私钥  
    * @param algorrithm 签名算法
    * @return String 十六进制签名字符串  
    * @throws Exception  
    */    
   public static String sign(byte[] data,String privateKey,String algorrithm) throws Exception    
   {    
   byte[] sign=sign(data,getKey(privateKey),algorrithm);    
   return new String(Hex.encodeHex(sign));    
   }
   /**  
    * 公钥校验  
    * @param data 待验证数据  
    * @param publicKey 公钥  
    * @param sign 签名  
    * @return boolean 成功返回true,失败返回false  
    * @throws Exception  
    */    
   public static boolean verify(byte[] data,String publicKey,String sign) throws Exception    
   {    
   return verify(data,getKey(publicKey),Hex.decodeHex(sign.toCharArray()));    
   }    
    
   /**  
    * 取得私钥十六进制表示形式  
    * @param keyMap 密钥Map  
    * @return String 私钥十六进制字符串  
    * @throws Exception  
    */    
   public static String getPrivateKeyString(Map<String,Object> keyMap) throws Exception    
   {    
   return new String(Hex.encodeHex(getPrivateKey(keyMap)));    
   }    
   /**  
    * 取得公钥十六进制表示形式  
    * @param keyMap 密钥Map  
    * @return String 公钥十六进制字符串  
    * @throws Exception  
    */    
   public static String getPublicKeyString(Map<String,Object> keyMap) throws Exception    
   {    
   return new String(Hex.encodeHex(getPublicKey(keyMap)));    
   }    
   /**  
    * 获取密钥  
    * @param key 密钥  
    * @return byte[] 密钥  
    * @throws Exception  
    */    
   public static byte[] getKey(String key) throws Exception    
   {    
	   return Hex.decodeHex(key.toCharArray());    
   }
   
   public static byte[] decryptByPublicKey(String cipherTextBase64, String modulus16,
			String exponent16) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
   
	   return decryptByPublicKey(new sun.misc.BASE64Decoder().decodeBuffer(cipherTextBase64),modulus16,exponent16);
   
   }
   
   public static byte[] decryptByPublicKey(byte[] datas, String modulus16,
			String exponent16) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
		
		byte[] modulusBuf = hexStringToBytes(modulus16);
		byte[] exponentBuf = hexStringToBytes(exponent16);
		BigInteger modulus = new BigInteger(1, modulusBuf);
		BigInteger publicExponent = new BigInteger(1, exponentBuf);

		RSAPublicKeySpec rsaPubKeySpec = new RSAPublicKeySpec(modulus,
				publicExponent);

		KeyFactory keyf = KeyFactory.getInstance("RSA");
		RSAPublicKey pubKey = (RSAPublicKey)keyf.generatePublic(rsaPubKeySpec);
		
		Cipher cipher = Cipher.getInstance(keyf.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		
		return cipher.doFinal(datas);
		
	}
   
   public static void main(String[] args) throws Exception {
//	   String enstr="iIlM5QIwbaz2UeapUVPZqKj9Z4y2M1j7kFsTbEBPMb4P2ShAeEzj9wMPPXPcrbS25X3qOli7RRBrBbSVy9B3qTz2w96b4byQwrhge9H3wFOXzXITFUVPZYWE+IoLTr74TxyW5ySzZke+Y++aQxYVLoDVN/jCZUb4kfJuToCUVks=";
//	   String module = "8D2E26E9729EA0BC4986E8C067F817E916A30DCD4CEDB515997183899DD8B3E8DE38C411D5282DA7082B8DBD3D798EB2308234B45388C23B5C9212CA4557A7F725C5796C625C696553E5282472BEF2EE3BB2D498C21A546D0948E3249C9D8EAB00D1E7BD4BE8ACF297F1985D81090E695D049ED4C510C6A3511630651462A599";
//		String ex ="010001";
//		String beforesha1 ="cSmSTbulWPwp2Znw/HVFEVmjMro=1046492014082610464917721001499203"; 
//		byte[] decryptByPublicKey = RSACoder.decryptByPublicKey(enstr, module, ex);
//		String result = bytesToHexString(decryptByPublicKey);
//		if(result.length()>40){
//			result=result.substring(result.length()-40);
//		}
//		//比较sha1相等
//		String sha1 = new SHA1().getDigestOfString(beforesha1.getBytes("utf8"));
//		System.out.println(sha1);
//		System.out.println(result);
//		System.out.println(verify(beforesha1.getBytes(),module,ex,enstr));
   }
   
   /**
    * 验证签名
 * @param data 待签名部分
 * @param module16 模数据
 * @param exponent16 key数据
 * @param signbase64 私钥签名数据
 * @return
 * @throws Exception
 */
public static boolean verify(byte[] data,String module16,String exponent16,String signbase64,byte[] appdataarr) throws Exception
   {
		log.debug("验证签名,base64的加密字符串为"+signbase64);
		if(log.isTraceEnabled()){
			
			log.trace("验证签名,模数据为"+module16);
			log.trace("验证签名,key数据为"+exponent16);
		}
	   byte[] decryptByPublicKey = RSACoder.decryptByPublicKey(signbase64, module16, exponent16);
	   String result = bytesToHexString(decryptByPublicKey);
	   log.debug("验证签名,转成16进制的字符串的结果为"+result);
	   if(result==null)
	   {
		   return false;
	   }
		if(result.length()>40){
			result=result.substring(result.length()-40);
		}
		if(log.isTraceEnabled()){
			log.trace("验证签名,取最后的40个字符，用于与sha1比较:"+result);
		}
		SHA1 sha1obj = new SHA1();
		String sha1 = sha1obj.getDigestOfString(data);   //中钞卡片算法
		
		log.debug("验证签名,中钞卡片算法，取最后的40个字符，用于与sha1"+sha1+"比较,比较结果:"+result.equalsIgnoreCase(sha1));
		if(!result.equalsIgnoreCase(sha1))
		{
			log.debug("尝试使用规范卡片算法进行验证");
			//最终的数据 0x00+0x7C+ appdata+0x7C+sha1
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			//byte[] appdataarr = appdata.getBytes("utf8");
			bo.write(0x00);
			bo.write(0x7C);
			bo.write(appdataarr);
			bo.write(0x7C);
			bo.write(sha1obj.getDigestOfBytes(data));
			String data2 = sha1obj.byteArrayToHexString(bo.toByteArray());
			log.debug("尝试使用规范卡片算法进行验证,其中应用数据为:"+sha1obj.byteArrayToHexString(appdataarr)+",整体数据为"+data2);
			
			
			String sha12 = sha1obj.getDigestOfString(bo.toByteArray());
			log.debug("验证签名,规范卡片算法，用于与sha12("+sha12+")比较,比较结果:"+result.equalsIgnoreCase(sha12));
			return result.equalsIgnoreCase(sha12);
		}
		return result.equalsIgnoreCase(sha1);
   }

/** 
 * @功能: BCD码转为10进制串(阿拉伯数据) 
 * @参数: BCD码 
 * @结果: 10进制串 
 */  
public static String bcd2Str(byte[] bytes) {  
    StringBuffer temp = new StringBuffer(bytes.length * 2);  
    for (int i = 0; i < bytes.length; i++) {  
        temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
        temp.append((byte) (bytes[i] & 0x0f));  
    }  
    return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
            .toString().substring(1) : temp.toString();  
}  

/** 
 * @功能: 10进制串转为BCD码 
 * @参数: 10进制串 
 * @结果: BCD码 
 */  
public static byte[] str2Bcd(String asc) {  
    int len = asc.length();  
    int mod = len % 2;  
    if (mod != 0) {  
        asc = "0" + asc;  
        len = asc.length();  
    }  
    byte abt[] = new byte[len];  
    if (len >= 2) {  
        len = len / 2;  
    }  
    byte bbt[] = new byte[len];  
    abt = asc.getBytes();  
    int j, k;  
    for (int p = 0; p < asc.length() / 2; p++) {  
        if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {  
            j = abt[2 * p] - '0';  
        } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {  
            j = abt[2 * p] - 'a' + 0x0a;  
        } else {  
            j = abt[2 * p] - 'A' + 0x0a;  
        }  
        if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {  
            k = abt[2 * p + 1] - '0';  
        } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {  
            k = abt[2 * p + 1] - 'a' + 0x0a;  
        } else {  
            k = abt[2 * p + 1] - 'A' + 0x0a;  
        }  
        int a = (j << 4) + k;  
        byte b = (byte) a;  
        bbt[p] = b;  
    }  
    return bbt;  
}  
   
   /**
	 * base64生成字符串
	 * @param byteData
	 * @return
	 */
	public static String getBase64ofString(byte[] byteData){
		return new String(Base64.encodeBase64((byteData)));
	}

   public static String bytesToHexString(byte[] src) {  
       StringBuilder stringBuilder = new StringBuilder("");  
       if (src == null || src.length <= 0) {  
           return null;  
       }  
       for (int i = 0; i < src.length; i++) {  
           int v = src[i] & 0xFF;  
           String hv = Integer.toHexString(v);  
           if (hv.length() < 2) {  
               stringBuilder.append(0);  
           }  
           stringBuilder.append(hv);  
       }  
       return stringBuilder.toString();  
   }  
   
   /** 
    * Convert hex string to byte[] 
    *  
    * @param hexString 
    *            the hex string 
    * @return byte[] 
    */  
   public static byte[] hexStringToBytes(String hexString) {  
       if (hexString == null || hexString.equals("")) {  
           return null;  
       }  
       hexString = hexString.toUpperCase();  
       int length = hexString.length() / 2;  
       char[] hexChars = hexString.toCharArray();  
       byte[] d = new byte[length];  
       for (int i = 0; i < length; i++) {  
           int pos = i * 2;  
           d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
       }  
       return d;  
   }  
   /** 
    * Convert char to byte 
    *  
    * @param c 
    *            char 
    * @return byte 
    */  
   private static byte charToByte(char c) {  
       return (byte) "0123456789ABCDEF".indexOf(c);  
   } 
}   