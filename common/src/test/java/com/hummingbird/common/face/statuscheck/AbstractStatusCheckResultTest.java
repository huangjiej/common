/**
 * 
 * AbstractStatusCheckResultTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.face.statuscheck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author john huang
 * 2015年4月10日 下午5:28:50
 * 本类主要做为
 */
public class AbstractStatusCheckResultTest {

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
	 * Test method for {@link com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult#getStatusLevel()}.
	 */
//	@Test
//	public void testGetStatusLevel() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult#getReport()}.
	 */
	@Test
	public void testGetReport() {
		SmsReporter smsReporter = new SmsReporter();
		HtmlReporter htmlRepoter = new HtmlReporter();
		AbstractStatusCheckResult sr = new AbstractStatusCheckResult();
		sr.setFunctionName("短信网关");
		sr.setStatusLevel(0);
		sr.setReport("接收短信300条");
		String report = sr.getResultReport();
		System.out.println(smsReporter.report(sr));
		AbstractStatusCheckResult sr1 = new AbstractStatusCheckResult();
		sr1.setFunctionName("短信网关");
		sr1.setStatusLevel(1);
		sr1.setReport("24小时内接收短信300条");
		String report1 = sr1.getResultReport();
		System.out.println(smsReporter.report(sr1));
		
		AbstractStatusCheckResult sr2 = new AbstractStatusCheckResult();
		sr2.setFunctionName("短信网关");
		AbstractStatusCheckResult sub1 = new AbstractStatusCheckResult();
		sub1.setStatusLevel(2);
		sub1.setReport("连接失败");
		AbstractStatusCheckResult sub2 = new AbstractStatusCheckResult();
		sub2.setStatusLevel(1);
		sub2.setReport("短信失败率大于30%");
		sr2.addItem(sub1);
		sr2.addItem(sub2);
		System.out.println(smsReporter.report(sr2));
		
		AbstractStatusCheckResult sr3 = new AbstractStatusCheckResult();
		sr3.setFunctionName("短信网关");
		AbstractStatusCheckResult sub31 = new AbstractStatusCheckResult();
		sub31.setStatusLevel(2);
		sub31.setReport("连接失败");
		AbstractStatusCheckResult sub32 = new AbstractStatusCheckResult();
		sub32.setStatusLevel(1);
		sub32.setReport("短信失败率大于30%");
		sr3.addItem(sub31);
		sub31.addItem(sub32);
		System.out.println(smsReporter.report(sr3));
		
		AbstractStatusCheckResult sr4 = new AbstractStatusCheckResult();
		sr4.setFunctionName("fnbilling计费网关");
		AbstractStatusCheckResult sub41 = new AbstractStatusCheckResult();
		//sub41.setStatusLevel(2);
		sub41.setFunctionName("订单");
		AbstractStatusCheckResult sub411 = new AbstractStatusCheckResult();
		sub411.setFunctionName("访问间隔");
		sub411.setStatusLevel(1);
		sub411.setReport("590秒前有访问");
		AbstractStatusCheckResult sub412 = new AbstractStatusCheckResult();
		sub412.setFunctionName("失败率");
		sub412.setStatusLevel(0);
		sub412.setReport("正常");
		sub41.addItem(sub411);
		sub41.addItem(sub412);
		
		AbstractStatusCheckResult sub42 = new AbstractStatusCheckResult();
		sub42.setFunctionName("通知");
		AbstractStatusCheckResult sub421 = new AbstractStatusCheckResult();
		sub421.setFunctionName("访问间隔");
		sub421.setStatusLevel(0);
		sub421.setReport("59秒前有访问");
		AbstractStatusCheckResult sub422 = new AbstractStatusCheckResult();
		sub422.setFunctionName("失败率");
		sub422.setStatusLevel(1);
		sub422.setReport("失败率有增大趋势");
		sub42.addItem(sub421);
		sub42.addItem(sub422);
		sr4.addItem(sub41);
		sr4.addItem(sub42);
		System.out.println(smsReporter.report(sr4));
		System.out.println(htmlRepoter.report(sr4));
		
		
		
	}

//	/**
//	 * Test method for {@link com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult#getResultReport()}.
//	 */
//	@Test
//	public void testGetResultReport() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult#addItem(com.hummingbird.common.face.statuscheck.StatusCheckItemResult)}.
//	 */
//	@Test
//	public void testAddItem() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult#getItemResults()}.
//	 */
//	@Test
//	public void testGetItemResults() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult#getFunctionName()}.
//	 */
//	@Test
//	public void testGetFunctionName() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult#setFunctionName(java.lang.String)}.
//	 */
//	@Test
//	public void testSetFunctionName() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link com.hummingbird.common.face.statuscheck.AbstractStatusCheckResult#setStatusLevel(int)}.
//	 */
//	@Test
//	public void testSetStatusLevel() {
//		fail("Not yet implemented");
//	}

}
