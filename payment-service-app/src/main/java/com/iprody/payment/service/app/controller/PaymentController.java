package com.iprody.payment.service.app.controller;

import com.iprody.payment.service.app.model.Payment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final Map<Long, Payment> payments = new HashMap<>();

    public PaymentController() {
        // Инициализация временного хранилища тестовыми данными
        payments.put(1L, new Payment(1L, "Payment for order #123", 100.50));
        payments.put(2L, new Payment(2L, "Payment for subscription", 50.00));
        payments.put(3L, new Payment(3L, "Refund for order #456", -25.00));
    }

    // Получение платежа по ID
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return payments.get(id);
    }

    // Получение всех платежей
    @GetMapping
    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments.values());
    }
}
