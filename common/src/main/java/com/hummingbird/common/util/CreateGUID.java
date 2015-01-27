package com.hummingbird.common.util;

import java.util.Random;
import java.util.UUID;

/**
 * 生成GUID
 * @author zch
 * @version 1.0 
 * 2011-3-21
 */
public class CreateGUID {

	/**
	 * 生成GUID
	 * @return GUID
	 */
	public static String createGuId(){
		UUID uuid = UUID.randomUUID();
		String uuIdStr = uuid.toString();
		uuIdStr = uuIdStr.replace("-", "");
		return uuIdStr;
	}
	
	/**
	 * 包装ID，以每num个字符加上"-"
	 * @author E.FLY
	 * @date 2012-12-11
	 * @time 下午03:51:08
	 * @param id
	 * @param num
	 * @return
	 */
	public static String wrapId(String id,int num) {
		String ids = "";
		if(id != null){
			for (int i = 0;i<id.length();i++) {
				ids += id.charAt(i);
				if(i%num==0 && i != 0){
					ids +="-";
				}
			}
		}else{
			return null;
		}
		return ids;
	}
	
	/**
	 * 创建订单号
	 * @return
	 */
	public static String genOrderNo(){
		return "UD00"+DateUtil.getCurrentTimeStr() + String.format("%014d", new Random().nextInt(999999999));
	}
}
