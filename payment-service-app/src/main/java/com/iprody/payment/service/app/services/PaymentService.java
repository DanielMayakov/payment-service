package com.iprody.payment.service.app.services;

import com.iprody.payment.service.app.dto.PaymentDto;
import com.iprody.payment.service.app.mapper.PaymentMapper;
import com.iprody.payment.service.app.model.Payment;
import com.iprody.payment.service.app.PaymentFilter;
import com.iprody.payment.service.app.PaymentFilterFactory;
import com.iprody.payment.service.app.model.PaymentStatus;
import com.iprody.payment.service.app.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
        Specification<Payment> spec = PaymentFilterFactory.fromFilter(filter);
        return paymentRepository.findAll(spec);
    }

    public Page<Payment> searchPaged(PaymentFilter filter, Pageable pageable) {
        Specification<Payment> spec = PaymentFilterFactory.fromFilter(filter);
        return paymentRepository.findAll(spec, pageable);
    }

    public PaymentDto get(UUID id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Платеж не найден: " + id));
    }

    public PaymentDto create(PaymentDto dto) {
        Payment entity = paymentMapper.toEntity(dto);
        entity.setId(null);
        Payment saved = paymentRepository.save(entity);
        return paymentMapper.toDto(saved);
    }

    public Page<PaymentDto> searchPagedDto(PaymentFilter filter, Pageable pageable) {
        Specification<Payment> spec = PaymentFilterFactory.fromFilter(filter);
        Page<Payment> page = paymentRepository.findAll(spec, pageable);
        return page.map(paymentMapper::toDto);
    }

    public PaymentDto update(UUID id, PaymentDto dto) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Платеж не найден: " + id);
        }
        Payment updated = paymentMapper.toEntity(dto);
        updated.setId(id);
        Payment saved = paymentRepository.save(updated);
        return paymentMapper.toDto(saved);
    }

    public void delete(UUID id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Платеж не найден: " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Transactional
    public PaymentDto updateStatus(UUID id, PaymentStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Платеж не найден: " + id));

        if (newStatus.equals(payment.getStatus())) {
            // Статус не изменился — возвращаем текущие данные
            return paymentMapper.toDto(payment);
        }

        payment.setStatus(newStatus);
        Payment saved = paymentRepository.save(payment);

        return paymentMapper.toDto(saved);
    }
}
