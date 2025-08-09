package com.iprody.payment.service.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a payment entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    /**
     * Unique identifier of the payment.
     */
    private Long id;

    /**
     * Human-readable description of the payment.
     */
    private String description;

    /**
     * Monetary amount of the payment.
     */
    private Double amount;
}
