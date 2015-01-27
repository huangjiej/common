package com.hummingbird.commonbiz.entity;

import java.util.Date;

public class SmsCode {
    private String mobilenum;

    private String appid;

    private String smscode;

    private Integer expirein;

    private Date sendtime;

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum == null ? null : mobilenum.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode == null ? null : smscode.trim();
    }

    public Integer getExpirein() {
        return expirein;
    }

    public void setExpirein(Integer expirein) {
        this.expirein = expirein;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

	@Override
	public String toString() {
		return "SmsCode [mobilenum=" + mobilenum + ", appid=" + appid
				+ ", smscode=" + smscode + ", expirein=" + expirein
				+ ", sendtime=" + sendtime + "]";
	}
}