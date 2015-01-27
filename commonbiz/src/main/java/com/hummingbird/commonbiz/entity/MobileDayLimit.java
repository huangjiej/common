package com.hummingbird.commonbiz.entity;

import java.util.Date;

public class MobileDayLimit {
    private Integer idtMobileDayLimit;

    private String sellerId;

    private String buyerId;

    private Date day;

    private Integer used;

    private Integer locker;

    public Integer getIdtMobileDayLimit() {
        return idtMobileDayLimit;
    }

    public void setIdtMobileDayLimit(Integer idtMobileDayLimit) {
        this.idtMobileDayLimit = idtMobileDayLimit;
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

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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