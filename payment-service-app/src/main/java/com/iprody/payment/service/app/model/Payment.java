package com.iprody.payment.service.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payment {
    private Long id;
    private String description;
    private Double amount;
}
