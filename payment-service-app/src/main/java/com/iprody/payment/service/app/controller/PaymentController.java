package com.iprody.payment.service.app.controller;

import com.iprody.payment.service.app.model.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class PaymentController {

    @GetMapping("/payments")
    public List<Payment> getAllPayments() {
        return Arrays.asList(
                new Payment(1L, "Payment for order #123", 100.50),
                new Payment(2L, "Payment for order #124", 200.75),
                new Payment(3L, "Payment for order #125", 300.00)
        );
    }
}
