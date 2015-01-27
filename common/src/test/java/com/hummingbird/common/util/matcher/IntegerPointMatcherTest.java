/**
 * 
 * IntegerPointMatcherTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.matcher;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hummingbird.common.exception.DataInvalidException;

/**
 * @author huangjiej_2
 * 2015年1月14日 上午10:05:56
 * 本类主要做为
 */
public class IntegerPointMatcherTest {

	
	
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
	 * Test method for {@link com.hummingbird.common.util.matcher.IntegerPointMatcher#initPattern(java.lang.String)}.
	 */
	@Test
	public void testInitPattern() {
		IntegerPointMatcher matcher = new IntegerPointMatcher();
		try {
			matcher.initPattern("3");
		} catch (DataInvalidException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		try {
			matcher.initPattern("3,4");
			fail("不支持的格式");
		} catch (DataInvalidException e) {
		
		}
		try {
			matcher.initPattern("3.4");
			fail("不支持的格式");
		} catch (DataInvalidException e) {
			
		}
		try {
			matcher.initPattern("3-4");
			fail("不支持的格式");
		} catch (DataInvalidException e) {
			
		}
	}

	/**
	 * Test method for {@link com.hummingbird.common.util.matcher.IntegerPointMatcher#match(java.lang.Object)}.
	 */
	@Test
	public void testMatch() {
		IntegerPointMatcher matcher = new IntegerPointMatcher();
		try {
			matcher.initPattern("3");
		} catch (DataInvalidException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		try {
			boolean match = matcher.match("3");
			assertTrue(match);
			
			match = matcher.match("4");
			assertFalse(match);
			match = matcher.match(4);
			assertFalse(match);
			match = matcher.match(3);
			assertTrue(match);
			match = matcher.match(null);
			assertFalse(match);
			try {
				match = matcher.match("");
				fail();
			} catch (Exception e) {
				
			}
			match = matcher.match(new BigDecimal("3"));
			assertTrue(match);
			match = matcher.match(new BigInteger("3"));
			assertTrue(match);
			match = matcher.match(3.14);
			assertTrue(match);
			match = matcher.match(3.84);
			assertTrue(match);
			try {
				match = matcher.match(new Date());
				fail();
			} catch (Exception e) {
				
			}
		} catch (DataInvalidException e) {
			e.printStackTrace();
			fail("匹配失败");
		}
		
	}

}
