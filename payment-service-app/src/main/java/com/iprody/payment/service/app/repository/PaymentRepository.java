package com.iprody.payment.service.app.repository;

import com.iprody.payment.service.app.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentRepository extends JpaRepository<Payment, UUID>,
        JpaSpecificationExecutor<Payment> {
}
