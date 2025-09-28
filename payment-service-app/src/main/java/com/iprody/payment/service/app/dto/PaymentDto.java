package com.iprody.payment.service.app.dto;

import com.iprody.payment.service.app.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PaymentDto {
    private UUID guid;
    private UUID inquiryRefId;
    private BigDecimal amount;
    private String currency;
    private UUID transactionRefId;
    private PaymentStatus status;
    private String note;
    private Instant createdAt;
    private Instant updatedAt;

    public PaymentDto() {
    }

    public PaymentDto(UUID id,
                      UUID inquiryRefId,
                      UUID transactionRefId,
                      BigDecimal amount,
                      String currency,
                      PaymentStatus status,
                      String note,
                      Instant createdAt,
                      Instant updatedAt) {
        this.guid = id;
        this.inquiryRefId = inquiryRefId;
        this.transactionRefId = transactionRefId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PaymentDto(UUID id,
                      UUID inquiryRefId,
                      UUID transactionRefId,
                      BigDecimal amount,
                      String currency,
                      PaymentStatus status,
                      String note,
                      OffsetDateTime createdAt,
                      OffsetDateTime updatedAt) {
        this(id,
                inquiryRefId,
                transactionRefId,
                amount,
                currency,
                status,
                note,
                createdAt != null ? createdAt.toInstant() : null,
                updatedAt != null ? updatedAt.toInstant() : null);
    }

    public static PaymentDto of(UUID id,
                                UUID inquiryRefId,
                                UUID transactionRefId,
                                BigDecimal amount,
                                String currency,
                                PaymentStatus status,
                                String note,
                                Instant createdAt,
                                Instant updatedAt) {
        return new PaymentDto(id, inquiryRefId, transactionRefId, amount, currency, status, note, createdAt, updatedAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID id() {
        return this.guid;
    }

    public static final class Builder {
        private UUID id;
        private UUID inquiryRefId;
        private UUID transactionRefId;
        private BigDecimal amount;
        private String currency;
        private PaymentStatus status;
        private String note;
        private Instant createdAt;
        private Instant updatedAt;

        private Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder inquiryRefId(UUID inquiryRefId) {
            this.inquiryRefId = inquiryRefId;
            return this;
        }

        public Builder transactionRefId(UUID transactionRefId) {
            this.transactionRefId = transactionRefId;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PaymentDto build() {
            return new PaymentDto(id, inquiryRefId, transactionRefId, amount, currency, status, note, createdAt, updatedAt);
        }
    }

    public UUID getId() {
        return guid;
    }

    public void setId(UUID id) {
        this.guid = id;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public UUID getInquiryRefId() {
        return inquiryRefId;
    }

    public void setInquiryRefId(UUID inquiryRefId) {
        this.inquiryRefId = inquiryRefId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UUID getTransactionRefId() {
        return transactionRefId;
    }

    public void setTransactionRefId(UUID transactionRefId) {
        this.transactionRefId = transactionRefId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
