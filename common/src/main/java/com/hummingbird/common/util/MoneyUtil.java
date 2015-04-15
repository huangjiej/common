package com.hummingbird.common.util;

import java.text.DecimalFormat;

public class MoneyUtil {
//	public static String getMoneyString10(int price){
//		StringBuffer sb=new StringBuffer();
//		for(int i=0;i<10-(price+"").length();i++){
//			sb.append(" ");
//		}
//		sb.append(""+price);
//		return sb.toString().trim();
//	}
	
	/**
	 * 把分转化为元并且精度为小数后1位，即角
	 * @param price
	 * @return
	 */
	public static String getMoneyStringDecimal2(Number price){
//		DecimalFormat df = new DecimalFormat("0.0");
//		String money=df.format(price);
//		if(money.startsWith(".")){
//			money="0"+money;
//		}
		
		return new DecimalFormat("0.0").format((double)(price.doubleValue()/100));
	}
	
	/**
	 * 格式化金额，到分
	 * @param price
	 * @return
	 */
	public static String getMoneyStringDecimal2fen(Number price){
		return new DecimalFormat("0.00").format((double)(price.doubleValue()/100));
		
	}
}
