package com.hummingbird.commonbiz.entity;

import java.util.Date;

public class OrderProcess {
	
	/**
	 * 短信下行，发送到用户手机
	 */
	public static final String SMS_TYPE_MT = "MT";
	/**
	 * 短信上行，从用户到移动
	 */
	public static final String SMS_TYPE_MO = "MO";
	
    private Integer IdtOrderProcess;

    private String buyerId;

    private String orderId;

    private Date insertTime;

    private String billingType;

    private String updown;

    private String smsport;

    private String smscontent;

    private String otherInfo;

    public Integer getIdtOrderProcess() {
        return IdtOrderProcess;
    }

    public void setIdtOrderProcess(Integer IdtOrderProcess) {
        this.IdtOrderProcess = IdtOrderProcess;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType == null ? null : billingType.trim();
    }

    public String getUpdown() {
        return updown;
    }

    public void setUpdown(String updown) {
        this.updown = updown == null ? null : updown.trim();
    }

    public String getSmsport() {
        return smsport;
    }

    public void setSmsport(String smsport) {
        this.smsport = smsport == null ? null : smsport.trim();
    }

    public String getSmscontent() {
        return smscontent;
    }

    public void setSmscontent(String smscontent) {
        this.smscontent = smscontent == null ? null : smscontent.trim();
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo == null ? null : otherInfo.trim();
    }
}