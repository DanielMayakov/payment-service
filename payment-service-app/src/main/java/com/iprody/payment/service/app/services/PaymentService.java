package com.iprody.payment.service.app.services;

import com.iprody.payment.service.app.dto.PaymentDto;
import com.iprody.payment.service.app.mapper.PaymentMapper;
import com.iprody.payment.service.app.model.Payment;
import com.iprody.payment.service.app.PaymentFilter;
import com.iprody.payment.service.app.PaymentFilterFactory;
import com.iprody.payment.service.app.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public List<Payment> search(PaymentFilter filter) {
        Specification<Payment> spec =
                PaymentFilterFactory.fromFilter(filter);
        return paymentRepository.findAll(spec);
    }


    public Page<Payment> searchPaged(PaymentFilter filter, Pageable
            pageable) {
        Specification<Payment> spec =
                PaymentFilterFactory.fromFilter(filter);
        return paymentRepository.findAll(spec, pageable);
    }

    public PaymentDto get(UUID id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Платеж не найден: " + id));
    }
}