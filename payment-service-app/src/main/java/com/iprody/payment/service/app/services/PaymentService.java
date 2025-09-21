package com.iprody.payment.service.app.services;

import com.iprody.payment.service.app.model.Payment;
import com.iprody.payment.service.app.PaymentFilter;
import com.iprody.payment.service.app.PaymentFilterFactory;
import com.iprody.payment.service.app.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
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
}
