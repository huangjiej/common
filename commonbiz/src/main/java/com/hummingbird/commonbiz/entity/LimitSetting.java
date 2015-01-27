package com.hummingbird.commonbiz.entity;

public class LimitSetting {
    private String sellerId;

    private Integer mobileDayLimit;

    private Integer mobileMonthLimit;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public Integer getMobileDayLimit() {
        return mobileDayLimit;
    }

    public void setMobileDayLimit(Integer mobileDayLimit) {
        this.mobileDayLimit = mobileDayLimit;
    }

    public Integer getMobileMonthLimit() {
        return mobileMonthLimit;
    }

    public void setMobileMonthLimit(Integer mobileMonthLimit) {
        this.mobileMonthLimit = mobileMonthLimit;
    }
}