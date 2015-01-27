package com.hummingbird.commonbiz.entity;

public class BlackBuyer {
	private String buyerId;
	private String backDesc;
	private String operator;
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	public String getBackDesc() {
		return backDesc;
	}
	public void setBackDesc(String backDesc) {
		this.backDesc = backDesc;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "BackBuyer [buyerId=" + buyerId + ", backDesc=" + backDesc
				+ ", operator=" + operator + "]";
	}
	
}
