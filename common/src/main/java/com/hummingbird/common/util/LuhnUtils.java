package com.hummingbird.common.util;

public class LuhnUtils {
	 public static void main(String[] args) {
	       // System.out.println(luhnTest("49927398716"));
	       // System.out.println(luhnTest("49927398717"));
	        //System.out.println(luhnTest("1234567812345678"));
	        System.out.println("此银行卡合法性--"+luhnTest("6226900906317074"));
	        System.out.println("检验码为--"+getCheckNum("622690090631707"));
	    }
	 
	    //验证银行卡号是否合法
	    public static boolean luhnTest(String number){
	    	//定义s1为奇数位总和，s2为偶数位总和
	        int s1 = 0, s2 = 0;
	        // 从最末一位开始提取，每一位上的数值
	        String reverse = new StringBuffer(number).reverse().toString();
	        for(int i = 0 ;i < reverse.length();i++){
	            int digit = Character.digit(reverse.charAt(i), 10);
	            //奇数位处理
	            if(i % 2 == 0){
	                s1 += digit;
	            }else{
	            	//偶数位处理，如果位数值乘以2大于10，取两位数之和，不大于10直接取乘积
	                s2 += 2 * digit;
	                if(digit >= 5){
	                    s2 -= 9;
	                }
	            }
	        }
	        //将奇数位和与偶数位和相加，除以10，如果能整除则为合法，否则为不合法
	        return (s1 + s2) % 10 == 0;
	    }
	    
	    //获取校验码
	    public static int  getCheckNum(String number){
	    	//定义s1为奇数位总和，s2为偶数位总和
	        int s1 = 0, s2 = 0;
	        int checkNum = 0;
	        // 从最末一位开始提取，每一位上的数值
	        String reverse = new StringBuffer(number).reverse().toString();
	        for(int i = 0 ;i < reverse.length();i++){
	            int digit = Character.digit(reverse.charAt(i), 10);
	            //奇数位处理
	            if(i % 2 == 0){
	                s2 += 2 * digit;
	                if(digit >= 5){
	                    s2 -= 9;
	                }
	            }else{
	            	//偶数位处理，如果位数值乘以2大于10，取两位数之和，不大于10直接取乘积
	            	 s1 += digit;
	            }
	        }
	        //将奇数位和与偶数位和相加，除以10，如果能整除则为合法，否则为不合法，取出尾数
	        String str = String.valueOf(s1 + s2); 
	        int lastNum = Integer.parseInt(String.valueOf(str.charAt(str.length()-1)));
	        //检验位得出
	        if(lastNum==0){
	        	 checkNum = 0;
	        }
	        else{
	        	 checkNum = 10-lastNum;
	        }
	        
	        
	        return checkNum;
	    }
}
