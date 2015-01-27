package com.hummingbird.commonbiz.vo;

/**
 * 支付网关通知接口
 * @author huangjiej_2
 * 2014年11月12日 下午5:17:53
 */
public class PayGateWayNotifyVo {
	private int errorcode;
	private String errormsg;
	private String dealId;
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	@Override
	public String toString() {
		return "NotifyVo [errorcode=" + errorcode + ", errormsg=" + errormsg
				+ ", dealId=" + dealId + "]";
	}
	
}
