/**
 * 
 * IPPointMatcherTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.matcher;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hummingbird.common.exception.DataInvalidException;

/**
 * @author huangjiej_2
 * 2015年1月14日 下午1:10:25
 * 本类主要做为
 */
public class IPPointMatcherTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.hummingbird.common.util.matcher.IPPointMatcher#initPattern(java.lang.String)}.
	 */
	@Test
	public void testInitPattern() {
		IPPointMatcher matcher = new IPPointMatcher();
		try {
			matcher.initPattern("3");
			fail();
		} catch (DataInvalidException e) {
		}
		try {
			matcher.initPattern("127.0.0.1");
		} catch (DataInvalidException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		try {
			matcher.initPattern("10.1-9.0.1-253");
		} catch (DataInvalidException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.hummingbird.common.util.matcher.IPPointMatcher#match(java.lang.Object)}.
	 */
	@Test
	public void testMatch() {
		IPPointMatcher matcher = new IPPointMatcher();
		try {
			matcher.initPattern("10.1-9.0.1-253");
		} catch (DataInvalidException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		try {
			boolean matchIpaddress = matcher.match("127.0.0.1");
			assertFalse(matchIpaddress);
			try {
				matchIpaddress = matcher.match(InetAddress.getLocalHost());
				assertFalse(matchIpaddress);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			try {
				matchIpaddress = matcher.match(new Date());
				assertFalse(matchIpaddress);
				
			} catch (Exception e) {
				//e.printStackTrace();
			}
			matchIpaddress = matcher.match(null);
			assertFalse(matchIpaddress);
			
		} catch (DataInvalidException e) {
			e.printStackTrace();
			fail("匹配失败");
		}
	}

	/**
	 * Test method for {@link com.hummingbird.common.util.matcher.IPPointMatcher#matchIpaddress(java.lang.String)}.
	 */
	@Test
	public void testMatchIpaddress() {
		IPPointMatcher matcher = new IPPointMatcher();
		try {
			matcher.initPattern("10.1-9.0.1-253");
		} catch (DataInvalidException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			boolean matchIpaddress = matcher.matchIpaddress("127.0.0.1");
			assertFalse(matchIpaddress);
			matchIpaddress = matcher.matchIpaddress("10.1.0.1");
			assertTrue(matchIpaddress);
			matchIpaddress = matcher.matchIpaddress("10.11.0.1");
			assertFalse(matchIpaddress);
			matchIpaddress = matcher.matchIpaddress("101.1.0.1");
			assertFalse(matchIpaddress);
			matchIpaddress = matcher.matchIpaddress("10.1.0.254");
			assertFalse(matchIpaddress);
			matchIpaddress = matcher.matchIpaddress("10.1.1.1");
			assertFalse(matchIpaddress);
		} catch (DataInvalidException e) {
			e.printStackTrace();
			fail("匹配失败");
		}
	}

	/**
	 * Test method for {@link com.hummingbird.common.util.matcher.IPPointMatcher#isIPAddress(java.lang.String)}.
	 */
	@Test
	public void testIsIPAddress() {
		IPPointMatcher matcher = new IPPointMatcher();
		assertTrue(matcher.isIPAddress("127.0.0.1"));
		assertFalse(matcher.isIPAddress("127.0.1"));
		assertFalse(matcher.isIPAddress("127"));
		
	}

}
