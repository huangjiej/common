/**
 * 
 * CertificateUtilsTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hummingbird.common.exception.SignatureException;

/**
 * @author john huang
 * 2015年2月7日 下午3:08:43
 * 本类主要做为
 */
public class CertificateUtilsTest {

	String certpath;
	String certpasswd = "app_map_front";
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		URL basepathurl = this.getClass() .getClassLoader().getResource("");
		String basepath = URLDecoder.decode(basepathurl.getFile());
		File cert = new File(basepath,"app_map_front.pfx");
		if(!cert.exists())
		{
			throw new FileNotFoundException("证书未找到:"+cert.getPath());
		}
		certpath=cert.getPath();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

//	/**
//	 * Test method for {@link com.hummingbird.common.util.CertificateUtils#signWithDigitalCert(java.lang.String[])}.
//	 */
//	@Test
//	public void testSignWithDigitalCertStringArray() {
//	}

	/**
	 * Test method for {@link com.hummingbird.common.util.CertificateUtils#signWithDigitalCert(java.lang.String, java.lang.String, java.lang.String[])}.
	 */
	@Test
	public void testSignWithDigitalCertStringStringStringArray() {
		try {
			String signature = CertificateUtils.signWithDigitalCert(certpath, certpasswd, "123","456");
			System.out.println("加密结果为:"+signature);
			PublicKey publicKeyformPfx = CertificateUtils.getPublicKeyformPfx(certpath, certpasswd);
			boolean verified = CertificateUtils.verifySignatureByPublicKey("123456".getBytes(), signature, publicKeyformPfx);
			Assert.assertTrue(verified);
		} catch (Exception e) {
			e.printStackTrace();
			fail("数字证书加密失败");
		}
		
	}

}
