package com.hummingbird.commonbiz.vo;


/**
 * 支付对象，用于进行支付
 * @author huangjiej_2
 * 2014年11月12日 下午4:11:24
 */
public class PaymentVO {

	/**
	 * 支付 号
	 */
	protected String paymentNo;
	/**
	 * 商家
	 */
	protected String sellerId;
	/**
	 * 商品
	 */
	protected String productId;
	/**
	 * 总金额
	 */
	protected int paymentAmount;
	/**
	 * 通知url
	 */
	protected String notifyUrl;
	/**
	 * 支付手机号
	 */
	protected String buyerId;
	/**
	 * 支付 号
	 */
	public String getPaymentNo() {
		return paymentNo;
	}
	/**
	 * 支付 号
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	/**
	 * 商家
	 */
	public String getSellerId() {
		return sellerId;
	}
	/**
	 * 商家
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * 商品
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 商品
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 总金额
	 */
	public int getPaymentAmount() {
		return paymentAmount;
	}
	/**
	 * 总金额
	 */
	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	/**
	 * 通知url
	 */
	public String getNotifyUrl() {
		return notifyUrl;
	}
	/**
	 * 通知url
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	/**
	 * 支付手机号
	 */
	public String getBuyerId() {
		return buyerId;
	}
	/**
	 * 支付手机号
	 */
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	@Override
	public String toString() {
		return "PaymentVO [paymentNo=" + paymentNo + ", sellerId=" + sellerId
				+ ", productId=" + productId + ", paymentAmount="
				+ paymentAmount + ", notifyUrl=" + notifyUrl + ", buyerId="
				+ buyerId + "]";
	}
	
	
}
