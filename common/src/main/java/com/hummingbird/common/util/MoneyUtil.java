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
	
	public static String getMoneyStringDecimal2(Number price){
//		DecimalFormat df = new DecimalFormat("0.0");
//		String money=df.format(price);
//		if(money.startsWith(".")){
//			money="0"+money;
//		}
		
		return new DecimalFormat("0.0").format((double)(price.doubleValue()/100));
	}
}
