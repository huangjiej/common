package com.hummingbird.commonbiz.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hummingbird.common.exception.SignatureException;
import com.hummingbird.common.util.EncodingUtil;


/**
 * <p>
 * 数字签名/加密解密工具包
 * </p>
 * 
 * @author IceWee
 * @date 2012-4-26
 * @version 1.0
 */
public class CertificateUtils

{

	private static final Log log = LogFactory.getLog(CertificateUtils.class);

	/**
	 * Java密钥库(Java 密钥库，JKS)KEY_STORE
	 */
	public static final String KEY_STORE = "JKS";

	public static final String X509 = "X.509";

	/**
	 * 文件读取缓冲区大小
	 */
	private static final int CACHE_SIZE = 2048;

	/**
	 * 最大文件加密块
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * 最大文件解密块
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * <p>
	 * 根据密钥库获得私钥
	 * </p>
	 * 
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	private static PrivateKey getPrivateKey(String keyStorePath, String alias,
			String password) throws Exception {
		KeyStore keyStore = getKeyStore(keyStorePath, password);
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias,
				getPasswordCharArray(password));
		return privateKey;
	}

	/**
	 * <p>
	 * 获得密钥库
	 * </p>
	 * 
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws KeyStoreException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws Exception
	 */
	private static KeyStore getKeyStore(String keyStorePath, String password) throws FileNotFoundException, IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException
	{
		KeyStore keyStore;
		try(FileInputStream in = new FileInputStream(keyStorePath)) {
			keyStore = KeyStore.getInstance(KEY_STORE);
			keyStore.load(in,getPasswordCharArray(password));
		} 
		return keyStore;
	}


	/**
	 * <p>
	 * 获得证书
	 * </p>
	 * 
	 * @param certificatePath
	 *            证书存储路径
	 * @return
	 * @throws CertificateException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	private static Certificate getCertificate(String certificatePath)
			throws CertificateException, FileNotFoundException, IOException {
		CertificateFactory certificateFactory;
		certificateFactory = CertificateFactory.getInstance(X509);
		java.security.cert.Certificate certificate =null;
		try(FileInputStream in =new FileInputStream(certificatePath)) {
			certificate = certificateFactory.generateCertificate(in);
		} 
		return certificate;
		
		
	}

	/**
	 * <p>
	 * 根据密钥库获得证书
	 * </p>
	 * 
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	private static Certificate getCertificate(String keyStorePath,
			String alias, String password) throws Exception  {
		KeyStore keyStore = getKeyStore(keyStorePath, password);
		Certificate certificate = keyStore.getCertificate(alias);
		return certificate;
	}
	
	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 * @param data
	 *            源数据
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey,String transformation) throws Exception {
		Cipher cipher;
		if(transformation!=null&&!transformation.trim().equals("")){
			cipher = Cipher.getInstance(transformation);
		}
		else{
			cipher = Cipher.getInstance(privateKey.getAlgorithm());
		}
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
		
		
	}

	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 * @param data
	 *            源数据
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath,
			String alias, String password) throws Exception {
		// 取得私钥
		PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * <p>
	 * 文件私钥加密
	 * </p>
	 * <p>
	 * 过大的文件可能会导致内存溢出 </>
	 * 
	 * @param filePath
	 *            文件路径
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptFileByPrivateKey(String filePath,
			String keyStorePath, String alias, String password)
			throws Exception {
		byte[] data = fileToByte(filePath);
		return encryptByPrivateKey(data, keyStorePath, alias, password);
	}

	/**
	 * <p>
	 * 文件加密
	 * </p>
	 * 
	 * @param srcFilePath
	 *            源文件
	 * @param destFilePath
	 *            加密后文件
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @throws Exception
	 */
	public static void encryptFileByPrivateKey(String srcFilePath,
			String destFilePath, String keyStorePath, String alias,
			String password) throws Exception {
		// 取得私钥
		PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		File srcFile = new File(srcFilePath);
		FileInputStream in = new FileInputStream(srcFile);
		File destFile = new File(destFilePath);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		destFile.createNewFile();
		OutputStream out = new FileOutputStream(destFile);
		byte[] data = new byte[MAX_ENCRYPT_BLOCK];
		byte[] encryptedData; // 加密块
		while (in.read(data) != -1) {
			encryptedData = cipher.doFinal(data);
			out.write(encryptedData, 0, encryptedData.length);
			out.flush();
		}
		out.close();
		in.close();
	}

	/**
	 * <p>
	 * 文件加密成BASE64编码的字符串
	 * </p>
	 * 
	 * @param filePath
	 *            文件路径
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	public static String encryptFileToBase64ByPrivateKey(String filePath,
			String keyStorePath, String alias, String password)
			throws Exception {
		byte[] encryptedData = encryptFileByPrivateKey(filePath, keyStorePath,
				alias, password);

		return new String(EncodingUtil.encodeBase64(encryptedData));
	}
	
	/**
	 * 获取密码字符串数组
	 * @param strPassword
	 * @return
	 */
	private static char[] getPasswordCharArray(String strPassword){
		char[] nPassword = null;
		if ((strPassword == null) || strPassword.trim().equals("")) {
			nPassword = null;
		} else {
			nPassword = strPassword.toCharArray();
		}
		return nPassword;
	}

	/**
	 * 从pfx证书中读取私钥
	 * 
	 * @param strPfx 文件路径
	 * @param strPassword 密码
	 * @return
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 */
	public static  PrivateKey getPrivateKeyformPfx(String strPfx, String strPassword) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
		
		try(FileInputStream fis = new FileInputStream(strPfx)) {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			ks.load(fis, getPasswordCharArray(strPassword));
			System.out.println("keystore type=" + ks.getType());
			// Now we loop all the aliases, we need the alias to get keys.
			// It seems that this value is the "Friendly name" field in the
			// detals tab <-- Certificate window <-- view <-- Certificate
			// Button <-- Content tab <-- Internet Options <-- Tools menu
			// In MS IE 6.
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
				System.out.println("alias=[" + keyAlias + "]");
			}
			// Now once we know the alias, we could get the keys.
			System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, getPasswordCharArray(strPassword));
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();
			System.out.println("cert class = " + cert.getClass().getName());
			System.out.println("cert = " + cert);
			System.out.println("public key = " + pubkey);
			System.out.println("private key = " + prikey);
			return prikey;
		} 
	}

	/**
	 * 从pfx证书中读取公钥
	 * 
	 * @param strPfx 文件路径
	 * @param strPassword 密码
	 * @return
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 */
	public static  PublicKey getPublicKeyformPfx(String strPfx,String strPassword) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
		char[] nPassword = null;
		if ((strPassword == null) || strPassword.trim().equals("")) {
			nPassword = null;
		} else {
			nPassword = strPassword.toCharArray();
		}
		try(FileInputStream fis = new FileInputStream(strPfx)) {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			ks.load(fis, nPassword);
			System.out.println("keystore type=" + ks.getType());
			// Now we loop all the aliases, we need the alias to get keys.
			// It seems that this value is the "Friendly name" field in the
			// detals tab <-- Certificate window <-- view <-- Certificate
			// Button <-- Content tab <-- Internet Options <-- Tools menu
			// In MS IE 6.
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
				System.out.println("alias=[" + keyAlias + "]");
			}
			// Now once we know the alias, we could get the keys.
			System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();
			System.out.println("cert class = " + cert.getClass().getName());
			System.out.println("cert = " + cert);
			System.out.println("public key = " + strPassword);
			return pubkey;
		} 
	}
	
	/**
	 * <p>
	 * 私钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData,
			String keyStorePath, String alias, String password)
			throws Exception {
		// 取得私钥
		PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 解密byte数组最大长度限制: 128
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param certificatePath
	 *            证书存储路径
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String certificatePath)
			throws Exception {
		// 取得公钥
		PublicKey publicKey = getPublicKey(certificatePath);
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param certificatePath
	 *            证书存储路径
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData,
			String certificatePath) throws Exception {
		PublicKey publicKey = getPublicKey(certificatePath);
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * <p>
	 * 文件解密
	 * </p>
	 * 
	 * @param srcFilePath
	 *            源文件
	 * @param destFilePath
	 *            目标文件
	 * @param certificatePath
	 *            证书存储路径
	 * @throws Exception
	 */
	public static void decryptFileByPublicKey(String srcFilePath,
			String destFilePath, String certificatePath) throws Exception {
		PublicKey publicKey = getPublicKey(certificatePath);
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		File srcFile = new File(srcFilePath);
		FileInputStream in = new FileInputStream(srcFile);
		File destFile = new File(destFilePath);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		destFile.createNewFile();
		OutputStream out = new FileOutputStream(destFile);
		byte[] data = new byte[MAX_DECRYPT_BLOCK];
		byte[] decryptedData; // 解密块
		while (in.read(data) != -1) {
			decryptedData = cipher.doFinal(data);
			out.write(decryptedData, 0, decryptedData.length);
			out.flush();
		}
		out.close();
		in.close();
	}

	/**
	 * <p>
	 * 生成数据签名
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, String keyStorePath, String alias,
			String password) throws Exception {
		// 获得证书
		X509Certificate x509Certificate = (X509Certificate) getCertificate(
				keyStorePath, alias, password);
		// 获取私钥
		KeyStore keyStore = getKeyStore(keyStorePath, password);
		// 取得私钥
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias,
				password.toCharArray());
		// 构建签名
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * <p>
	 * 生成数据签名并以BASE64编码
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	public static String signToBase64(byte[] data, String keyStorePath,
			String alias, String password) throws Exception {
		return new String(EncodingUtil.encodeBase64(sign(data, keyStorePath,
				alias, password)));
	}

	/**
	 * <p>
	 * 生成文件数据签名(BASE64)
	 * </p>
	 * <p>
	 * 需要先将文件私钥加密，再根据加密后的数据生成签名(BASE64)，适用于小文件
	 * </p>
	 * 
	 * @param filePath
	 *            源文件
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	public static String signFileToBase64WithEncrypt(String filePath,
			String keyStorePath, String alias, String password)
			throws Exception {
		byte[] encryptedData = encryptFileByPrivateKey(filePath, keyStorePath,
				alias, password);
		return signToBase64(encryptedData, keyStorePath, alias, password);
	}

	/**
	 * <p>
	 * 生成文件签名
	 * </p>
	 * <p>
	 * 注意：<br>
	 * 方法中使用了FileChannel，其巨大Bug就是不会释放文件句柄，导致签名的文件无法操作(移动或删除等)<br>
	 * 该方法已被generateFileSign取代
	 * </p>
	 * 
	 * @param filePath
	 *            文件路径
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static byte[] signFile(String filePath, String keyStorePath,
			String alias, String password) throws Exception {
		byte[] sign = new byte[0];
		// 获得证书
		X509Certificate x509Certificate = (X509Certificate) getCertificate(
				keyStorePath, alias, password);
		// 获取私钥
		KeyStore keyStore = getKeyStore(keyStorePath, password);
		// 取得私钥
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias,
				password.toCharArray());
		// 构建签名
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initSign(privateKey);
		File file = new File(filePath);
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			FileChannel fileChannel = in.getChannel();
			MappedByteBuffer byteBuffer = fileChannel.map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			signature.update(byteBuffer);
			fileChannel.close();
			in.close();
			sign = signature.sign();
		}
		return sign;
	}

	/**
	 * <p>
	 * 生成文件数字签名
	 * </p>
	 * 
	 * <p>
	 * <b>注意：</b><br>
	 * 生成签名时update的byte数组大小和验证签名时的大小应相同，否则验证无法通过
	 * </p>
	 * 
	 * @param filePath
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static byte[] generateFileSign(String filePath, String keyStorePath,
			String alias, String password) throws Exception {
		byte[] sign = new byte[0];
		// 获得证书
		X509Certificate x509Certificate = (X509Certificate) getCertificate(
				keyStorePath, alias, password);
		// 获取私钥
		KeyStore keyStore = getKeyStore(keyStorePath, password);
		// 取得私钥
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias,
				password.toCharArray());
		// 构建签名
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initSign(privateKey);
		File file = new File(filePath);
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			byte[] cache = new byte[CACHE_SIZE];
			int nRead = 0;
			while ((nRead = in.read(cache)) != -1) {
				signature.update(cache, 0, nRead);
			}
			in.close();
			sign = signature.sign();
		}
		return sign;
	}

	/**
	 * <p>
	 * 文件签名成BASE64编码字符串
	 * </p>
	 * 
	 * @param filePath
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String signFileToBase64(String filePath, String keyStorePath,
			String alias, String password) throws Exception {
		return new String(EncodingUtil.encodeBase64(generateFileSign(filePath,
				keyStorePath, alias, password)));
	}

	/**
	 * <p>
	 * 验证签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param sign
	 *            数据签名[BASE64]
	 * @param certificatePath
	 *            证书存储路径
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(byte[] data, String sign,
			String certificatePath) throws Exception {
		// 获得证书
		X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
		// 获得公钥
		PublicKey publicKey = x509Certificate.getPublicKey();
		// 构建签名
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initVerify(publicKey);
		signature.update(data);
		return signature.verify(EncodingUtil.decodeBase64(sign.getBytes()));
	}

	/**
	 * <p>
	 * 校验文件完整性
	 * </p>
	 * <p>
	 * 鉴于FileChannel存在的巨大Bug，该方法已停用，被validateFileSign取代
	 * </p>
	 * 
	 * @param filePath
	 *            文件路径
	 * @param sign
	 *            数据签名[BASE64]
	 * @param certificatePath
	 *            证书存储路径
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static boolean verifyFileSign(String filePath, String sign,
			String certificatePath) throws Exception {
		boolean result = false;
		// 获得证书
		X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
		// 获得公钥
		PublicKey publicKey = x509Certificate.getPublicKey();
		// 构建签名
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initVerify(publicKey);
		File file = new File(filePath);
		if (file.exists()) {
			byte[] decodedSign = EncodingUtil.decodeBase64(sign.getBytes());
			FileInputStream in = new FileInputStream(file);
			FileChannel fileChannel = in.getChannel();
			MappedByteBuffer byteBuffer = fileChannel.map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			signature.update(byteBuffer);
			in.close();
			result = signature.verify(decodedSign);
		}
		return result;
	}

	/**
	 * <p>
	 * 校验文件签名
	 * </p>
	 * 
	 * @param filePath
	 * @param sign
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 */
	public static boolean validateFileSign(String filePath, String sign,
			String certificatePath) throws Exception {
		boolean result = false;
		// 获得证书
		X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
		// 获得公钥
		PublicKey publicKey = x509Certificate.getPublicKey();
		// 构建签名
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initVerify(publicKey);
		File file = new File(filePath);
		if (file.exists()) {
			byte[] decodedSign = EncodingUtil.decodeBase64(sign.getBytes());
			FileInputStream in = new FileInputStream(file);
			byte[] cache = new byte[CACHE_SIZE];
			int nRead = 0;
			while ((nRead = in.read(cache)) != -1) {
				signature.update(cache, 0, nRead);
			}
			in.close();
			result = signature.verify(decodedSign);
		}
		return result;
	}

	/**
	 * <p>
	 * BASE64解码->签名校验
	 * </p>
	 * 
	 * @param base64String
	 *            BASE64编码字符串
	 * @param sign
	 *            数据签名[BASE64]
	 * @param certificatePath
	 *            证书存储路径
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyBase64Sign(String base64String, String sign,
			String certificatePath) throws Exception {
		byte[] data = EncodingUtil.decodeBase64(base64String.getBytes());
		return verifySign(data, sign, certificatePath);
	}

	/**
	 * <p>
	 * BASE64解码->公钥解密-签名校验
	 * </p>
	 * 
	 * 
	 * @param base64String
	 *            BASE64编码字符串
	 * @param sign
	 *            数据签名[BASE64]
	 * @param certificatePath
	 *            证书存储路径
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyBase64SignWithDecrypt(String base64String,
			String sign, String certificatePath) throws Exception {
		byte[] encryptedData = EncodingUtil.decodeBase64(base64String
				.getBytes());
		byte[] data = decryptByPublicKey(encryptedData, certificatePath);
		return verifySign(data, sign, certificatePath);
	}

	/**
	 * <p>
	 * 文件公钥解密->签名校验
	 * </p>
	 * 
	 * @param encryptedFilePath
	 *            加密文件路径
	 * @param sign
	 *            数字证书[BASE64]
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyFileSignWithDecrypt(String encryptedFilePath,
			String sign, String certificatePath) throws Exception {
		byte[] encryptedData = fileToByte(encryptedFilePath);
		byte[] data = decryptByPublicKey(encryptedData, certificatePath);
		return verifySign(data, sign, certificatePath);
	}

	/**
	 * <p>
	 * 校验证书当前是否有效
	 * </p>
	 * 
	 * @param certificate
	 *            证书
	 * @return
	 */
	public static boolean verifyCertificate(Certificate certificate) {
		return verifyCertificate(new Date(), certificate);
	}

	/**
	 * <p>
	 * 验证证书是否过期或无效
	 * </p>
	 * 
	 * @param date
	 *            日期
	 * @param certificate
	 *            证书
	 * @return
	 */
	public static boolean verifyCertificate(Date date, Certificate certificate) {
		boolean isValid = true;
		try {
			X509Certificate x509Certificate = (X509Certificate) certificate;
			x509Certificate.checkValidity(date);
		} catch (Exception e) {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * <p>
	 * 验证数字证书是在给定的日期是否有效
	 * </p>
	 * 
	 * @param date
	 *            日期
	 * @param certificatePath
	 *            证书存储路径
	 * @return
	 */
	public static boolean verifyCertificate(Date date, String certificatePath) {
		Certificate certificate;
		try {
			certificate = getCertificate(certificatePath);
			return verifyCertificate(certificate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * <p>
	 * 验证数字证书是在给定的日期是否有效
	 * </p>
	 * 
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 */
	public static boolean verifyCertificate(Date date, String keyStorePath,
			String alias, String password) {
		Certificate certificate;
		try {
			certificate = getCertificate(keyStorePath, alias, password);
			return verifyCertificate(certificate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * <p>
	 * 验证数字证书当前是否有效
	 * </p>
	 * 
	 * @param keyStorePath
	 *            密钥库存储路径
	 * @param alias
	 *            密钥库别名
	 * @param password
	 *            密钥库密码
	 * @return
	 */
	public static boolean verifyCertificate(String keyStorePath, String alias,
			String password) {
		return verifyCertificate(new Date(), keyStorePath, alias, password);
	}

	/**
	 * <p>
	 * 验证数字证书当前是否有效
	 * </p>
	 * 
	 * @param certificatePath
	 *            证书存储路径
	 * @return
	 */
	public static boolean verifyCertificate(String certificatePath) {
		return verifyCertificate(new Date(), certificatePath);
	}

	/**
	 * <p>
	 * 文件转换为byte数组
	 * </p>
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws Exception
	 */
	public static byte[] fileToByte(String filePath) throws Exception {
		byte[] data = new byte[0];
		File file = new File(filePath);
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
			byte[] cache = new byte[CACHE_SIZE];
			int nRead = 0;
			while ((nRead = in.read(cache)) != -1) {
				out.write(cache, 0, nRead);
				out.flush();
			}
			out.close();
			in.close();
			data = out.toByteArray();
		}
		return data;
	}

	/**
	 * <p>
	 * 二进制数据写文件
	 * </p>
	 * 
	 * @param bytes
	 *            二进制数据
	 * @param filePath
	 *            文件生成目录
	 */
	public static void byteArrayToFile(byte[] bytes, String filePath)
			throws Exception {
		InputStream in = new ByteArrayInputStream(bytes);
		File destFile = new File(filePath);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		destFile.createNewFile();
		OutputStream out = new FileOutputStream(destFile);
		byte[] cache = new byte[CACHE_SIZE];
		int nRead = 0;
		while ((nRead = in.read(cache)) != -1) {
			out.write(cache, 0, nRead);
			out.flush();
		}
		out.close();
		in.close();
	}

	/**
	 * 从证书中提取公钥进行验签
	 * 
	 * @param data
	 * @param signature64
	 * @param certPath
	 * @return
	 * @throws SignatureException
	 * @throws Exception
	 */
	public static boolean verifySignatureByCert(byte[] data,
			String signature64, String certPath) throws SignatureException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("从证书中提取公钥进行验签开始，证书路径为%s，签名为%s", certPath,
					signature64));
		}
		FileInputStream fin=null;
		try {
			byte[] signatureBuf = new sun.misc.BASE64Decoder()
					.decodeBuffer(signature64);

			CertificateFactory certificatefactory = CertificateFactory
					.getInstance("X.509");
			fin = new FileInputStream(certPath);
			X509Certificate certificate = (X509Certificate) certificatefactory
					.generateCertificate(fin);
			PublicKey pub = certificate.getPublicKey();
			Signature rsa = Signature.getInstance("SHA1withRSA");
			rsa.initVerify(pub);
			rsa.update(data);

			return rsa.verify(signatureBuf);
		} catch (IOException e) {
			log.error("从证书中提取公钥进行验签出错", e);
			throw new SignatureException("验签失败，过程中出现错误");
		} catch (CertificateException e) {
			log.error("从证书中提取公钥进行验签出错", e);
			throw new SignatureException("验签失败，过程中出现错误");
		} catch (NoSuchAlgorithmException e) {
			log.error("从证书中提取公钥进行验签出错", e);
			throw new SignatureException("验签失败，过程中出现错误");
		} catch (InvalidKeyException e) {
			log.error("从证书中提取公钥进行验签出错", e);
			throw new SignatureException("验签失败，过程中出现错误");
		} catch (java.security.SignatureException e) {
			log.error("从证书中提取公钥进行验签出错", e);
			throw new SignatureException("验签失败，过程中出现错误");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug(String.format("从证书中提取公钥进行验签完成"));
			}
			if(fin!=null){
				try {
					fin.close();
				} catch (IOException e) {
					log.error("关闭证书文件出错",e);
				}
			}
		}
	}
	
//	public static byte[] signByKey(byte[] data,Key pkey){
//		try{
//			Signature signature = Signature.getInstance("SHA1WithRSA");
//			signature.initSign(pkey);
//			signature.update(data);
//			byte[] signedData = signature.sign();
//
//			return signedData;
//		} catch (Exception e) {
//			log.error("从证书中提取私钥进行签名出错", e);
//			throw new SignatureException("签名失败，过程中出现错误");
//		} finally {
//			if (log.isDebugEnabled()) {
//				log.debug(String.format("从证书中提取私钥进行签名完成"));
//			}
//		}
//	}

	/**
	 * pfx证书,私钥进行签名
	 * @param data
	 * @param certPath
	 * @param password
	 * @return
	 * @throws SignatureException
	 */
	public static byte[] signByCert(byte[] data, String certPath,
			String password) throws SignatureException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("从证书中提取私钥进行签名开始，证书路径为%s", certPath));
		}
//		char[] nPassword = null;
//		if ((password == null) || password.trim().equals("")) {
//			nPassword = null;
//		} else {
//			nPassword = password.toCharArray();
//		}
		
		try{
			PrivateKey pKey = getPrivateKeyformPfx(certPath, password);
//			KeyStore ks = KeyStore.getInstance("pkcs12");
//			ks.load(fis, nPassword);
//
//			String alias = ks.aliases().nextElement();
//			PrivateKey pKey = (PrivateKey) ks.getKey(alias,nPassword);
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initSign(pKey);
			signature.update(data);
			byte[] signedData = signature.sign();

			return signedData;
		} catch (Exception e) {
			log.error("从证书中提取私钥进行签名出错", e);
			throw new SignatureException("签名失败，过程中出现错误");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug(String.format("从证书中提取私钥进行签名完成"));
			}
		}
	}
	
	public static void main(String[] args) {
    	String mingwen = "汗滴和下土";
    	String certpath = "C:\\Users\\huangjiej_2\\Desktop\\x.pfx";
    	String password="123";
		try {
			byte[] signature = signByCert(Base64.decodeBase64(mingwen), certpath,password);
			System.out.println(Base64.encodeBase64String(signature));
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}
	
	/** 
     * 读取公钥 
	 * @return 
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
     */  
    public  static PublicKey getPublicKeyFromPem(Reader reader) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {  
        try(BufferedReader br = new BufferedReader(reader)) {
            String s = br.readLine();  
            StringBuffer publickey = new StringBuffer();  
            s = br.readLine();  
            while (s.charAt(0) != '-') {
                publickey.append(s + "\r");
                s = br.readLine();  
            }
            if (log.isDebugEnabled()) {
            	System.out.println(String.format("读取pem中的公钥原始信息为%s",publickey.toString()));
				log.debug(String.format("读取pem中的公钥原始信息为%s",publickey.toString()));
			}
            byte[] keybyte = Base64.decodeBase64(publickey.toString());
            KeyFactory kf = KeyFactory.getInstance("DSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybyte); 
            PublicKey pkey = kf.generatePublic(keySpec);
            if (log.isDebugEnabled()) {
            	System.out.println(String.format("读取pem中的公钥信息，转换后为%s",Base64.encodeBase64String(pkey.getEncoded())));
            	log.debug(String.format("读取pem中的公钥信息，转换后为%s",pkey.getEncoded()));
            }
            return pkey;
        } 
    }
    
    /** 
     * 读取公钥 
	 * @return 
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
     * @throws CertificateException 
     */  
    public  static PublicKey getPublicKeyFromCer(InputStream bais) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, CertificateException {  
        
    	CertificateFactory certificatefactory=CertificateFactory.getInstance("X.509");
    	try{
    		X509Certificate Cert = (X509Certificate)certificatefactory.generateCertificate(bais);
    		PublicKey pk = Cert.getPublicKey();
    		return pk;
    	}
    	finally{
    		bais.close();
    	}
    	
    }
    
    /**
     * 从cer证书中获取公钥
     * @param certfilename
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws CertificateException
     */
    public static PublicKey getPublicKeyFromCer(String certfilename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, CertificateException {
    	return getPublicKeyFromCer(new FileInputStream(certfilename));
    }

	/**
	 * @param decodeBase64
	 * @param publicKey
	 * @param cipher_value
	 * @return
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws IOException 
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData,
			PublicKey publicKey, String cipher_value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		Cipher cipher;
		if(cipher_value==null||cipher_value.trim().equals("")){
			 cipher = Cipher.getInstance(publicKey.getAlgorithm());
		}
		else{
			cipher = Cipher.getInstance(cipher_value);
			
		}
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		int inputLen = encryptedData.length;
		try(ByteArrayOutputStream out = new ByteArrayOutputStream())
		{
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher
							.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher
							.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			return decryptedData;
		}
	}
	
	 /**
     * 获取公钥
     * @param publickeybase64
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String publickeybase64) throws NoSuchAlgorithmException, InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] encodedKey = publickeybase64.getBytes();
        // 先base64解码
        encodedKey = Base64.decodeBase64(encodedKey);
        PublicKey generatePublic = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        return generatePublic;
    }
    
    /**
     * 根据公钥进行验签
     * @param data
     * @param signature64
     * @param publickeybase64
     * @return
     * @throws SignatureException
     */
    public static boolean verifySignatureByPublicKey(byte[] data,
			String signature64, String publickeybase64 ) throws SignatureException{
    	PublicKey pkey;
		try {
			pkey = getPublicKey(publickeybase64);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error(String.format("生成公钥失败"),e);
			throw new SignatureException("验签失败", e);
		}
    	return verifySignatureByPublicKey(data,signature64,pkey);
    }
    
    /**
     * 根据公钥进行验签
     * @param data
     * @param signature64
     * @param pkey
     * @return
     * @throws SignatureException
     */
    public static boolean verifySignatureByPublicKey(byte[] data,
			String signature64, PublicKey pkey ) throws SignatureException{
    	if (log.isDebugEnabled()) {
			log.debug(String.format("根据公钥字符串验签"));
		}
    	try{
//    		Signature rsa = Signature.getInstance("RSA");
    		Signature rsa = Signature.getInstance("SHA1withRSA");
    		rsa.initVerify(pkey);
    		rsa.update(data);
    		byte[] signatureBuf = Base64.decodeBase64(signature64);
    		return rsa.verify(signatureBuf);
    	}
    	catch(Exception e){
    		throw new SignatureException("验签失败", e);
    	}
    	
    }

}
