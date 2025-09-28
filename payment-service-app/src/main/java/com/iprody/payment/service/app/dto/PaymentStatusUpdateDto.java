package com.iprody.payment.service.app.dto;

import com.iprody.payment.service.app.model.PaymentStatus;
import org.antlr.v4.runtime.misc.NotNull;

public class PaymentStatusUpdateDto {
    @NotNull
    private PaymentStatus status;

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

}
