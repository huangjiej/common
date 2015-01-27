package com.hummingbird.commonbiz.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hummingbird.common.exception.RequestException;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.http.HttpRequester;
import com.hummingbird.common.util.json.JSONException;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.commonbiz.vo.PaymentVO;

/**
 * 支付相关工具
 * @author huangjiej_2
 * 2014年11月12日 下午1:05:14
 */
public class PaymentUtil {

	protected static final Log log = LogFactory.getLog(PaymentUtil.class);
	
	/**
	 * 请求进行支付
	 * @param paycallback
	 * @throws RequestException
	 */
	public static void callPay(PaymentCallback paycallback) throws RequestException{

		PaymentVO paymentVo = paycallback.getPaymentVo();
		if(paymentVo==null){
			throw new RequestException("支付对象为空");
		}
		
		String requestJson = null;
		JSONObject jo = new JSONObject(paymentVo);
		jo.remove("class");
		requestJson = jo.toString();
		
		String paygatewayUrl = new PropertiesUtil().getProperty("paygatewayUrl");
		if(log.isDebugEnabled()){
			log.debug("发起支付请求:"+requestJson);
		}
		String result = new HttpRequester().postRequest(paygatewayUrl,
				requestJson);
		if(log.isDebugEnabled()){
			log.debug("发起支付请求完成");
		}
		if (StringUtils.isNotBlank(result)) {
			log.debug("result=" + result);
			JSONObject obj;
			try {
				obj = new JSONObject(result);
			} catch (JSONException e) {
				log.error("请求网关结果解析异常！",e);
				throw new RequestException("请求网关结果解析异常！",e);
				//rm.setErr(1275, "请求网关结果解析异常！");
			}
			boolean successed = true;
			String errcode;
			String errmsg;
			try {
				errcode = obj.getString("errcode");
				errmsg = obj.getString("errmsg");
			} catch (JSONException e) {
				throw new RequestException("请求网关结果解析异常！",e);
			}
			// 如果请求网关支付成功
			if ("0".equals(errcode)) {
				if(log.isDebugEnabled()){
					log.debug("支付网关已接受请求");
				}
//					rm.setErr(0, "请求网关支付成！");
			} else {
				if(log.isDebugEnabled()){
					log.debug("支付网关不接受请求，返回错误信息:"+errmsg);
				}
				successed=false;
//					rm.setErr(1274, obj.getString("errmsg"));
			}
			paycallback.requestResponse(paymentVo, successed, errmsg);
				

		} else {
			if(log.isDebugEnabled()){
				log.debug("支付网关无返回任何内容");
			}
			paycallback.requestResponse(paymentVo, false, "支付网关无返回任何内容");
		}
//		// 如果请求网关失败，设置订单为失败状态
//		if (rm.getErrcode() != 0) {
//			order.setStatus(OrderStatus.F);
//		}
//		orderMapper.updateOrderInfo(order);
	
	}
	
	/**
	 * 支付参数接口
	 * @author huangjiej_2
	 * 2014年11月12日 下午4:31:17
	 */
	public static interface PaymentCallback{
		
		/**
		 * 生成支付对象
		 * @return
		 */
		public PaymentVO getPaymentVo();
		
		/**
		 * 提交支付返回结果处理
		 * @param paymentvo
		 * @param result
		 */
		public void requestResponse(PaymentVO paymentvo,boolean success,String result );
	}
}
