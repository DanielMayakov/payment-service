package com.iprody.payment.service.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Payment Service Spring Boot application.
 */
@SpringBootApplication
public class PaymentServiceApplication {

    /**
     * Private constructor to prevent instantiation of utility-like class.
     */
    private PaymentServiceApplication() {
        // Intentionally empty.
    }

    /**
     * Application entry point.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
