package com.hummingbird.common.vo;

public abstract class Signaturable {

	/**
	 * 时间错
	 */
	protected String timeStamp;	
	/**
	 * 随机数
	 */
	protected String nonce;	
	/**
	 * 签名
	 */
	protected String signature;
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
}
