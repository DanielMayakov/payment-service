package com.iprody.payment.service.app.controller;

import com.iprody.payment.service.app.dto.PaymentDto;
import com.iprody.payment.service.app.PaymentFilter;
import com.iprody.payment.service.app.dto.PaymentStatusUpdateDto;
import com.iprody.payment.service.app.services.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@Validated
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto create(@RequestBody @Valid PaymentDto dto) { // добавили @Valid
        return paymentService.create(dto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin', 'reader')")
    public PaymentDto get(@PathVariable UUID id) {
        return paymentService.get(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('admin', 'reader', 'user')")
    public Page<PaymentDto> search(@ModelAttribute PaymentFilter filter, Pageable pageable) {
        return paymentService.searchPagedDto(filter, pageable);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('admin', 'reader')")
    public Page<PaymentDto> searchPayments(
            @ModelAttribute PaymentFilter filter,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(500) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Set<String> allowedSorts = Set.of("createdAt", "status", "amount");
        if (!allowedSorts.contains(sortBy)) {
            throw new IllegalArgumentException("Unsupported sortBy=" + sortBy);
        }

        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return paymentService.searchPagedDto(filter, pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin', 'reader')")
    public PaymentDto update(@PathVariable UUID id, @RequestBody @Valid PaymentDto dto) { // добавили @Valid
        return paymentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        paymentService.delete(id);
    }

    @PatchMapping("/{id}/status")
    public PaymentDto updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid PaymentStatusUpdateDto dto
    ) {
        return paymentService.updateStatus(id, dto.getStatus());
    }
}

