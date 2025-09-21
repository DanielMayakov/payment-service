package com.iprody.payment.service.app;

import com.iprody.payment.service.app.model.PaymentStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class PaymentFilter {
    private String currency;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Instant createdAfter;
    private Instant createdBefore;
    private List<PaymentStatus> statuses;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Instant getCreatedAfter() {
        return createdAfter;
    }

    public void setCreatedAfter(Instant createdAfter) {
        this.createdAfter = createdAfter;
    }

    public Instant getCreatedBefore() {
        return createdBefore;
    }

    public void setCreatedBefore(Instant createdBefore) {
        this.createdBefore = createdBefore;
    }

    public List<PaymentStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<PaymentStatus> statuses) {
        this.statuses = statuses;
    }
}
