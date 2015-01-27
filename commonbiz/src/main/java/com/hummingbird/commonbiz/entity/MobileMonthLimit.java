package com.hummingbird.commonbiz.entity;

import java.util.Date;

public class MobileMonthLimit {
    private Integer idtMobileMonthLimit;

    private String sellerId;

    private String buyerId;

    private Date month;

    private Integer used;

    private Integer locker;

    public Integer getIdtMobileMonthLimit() {
        return idtMobileMonthLimit;
    }

    public void setIdtMobileMonthLimit(Integer idtMobileMonthLimit) {
        this.idtMobileMonthLimit = idtMobileMonthLimit;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Integer getLocker() {
        return locker;
    }

    public void setLocker(Integer locker) {
        this.locker = locker;
    }
}