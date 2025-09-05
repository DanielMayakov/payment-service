package com.iprody.payment.service.app.services;

import com.iprody.payment.service.app.dto.PaymentDto;
import com.iprody.payment.service.app.PaymentFilter;
import com.iprody.payment.service.app.model.Payment;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PaymentService {
    PaymentDto get(UUID id);

    Page<PaymentDto> search(PaymentFilter filter, Pageable pageable);

    Page<Payment> searchPaged(PaymentFilter filter, Pageable pageable);
}