package com.iprody.payment.service.app.repository;

import com.iprody.payment.service.app.model.Payment;
import com.iprody.payment.service.app.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByStatus(PaymentStatus status);
}
