package com.iprody.payment.service.app.mapper;

import com.iprody.payment.service.app.dto.PaymentDto;
import com.iprody.payment.service.app.model.Payment;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);

    Payment toEntity(PaymentDto dto);

    default Instant map(OffsetDateTime odt) {
        return odt == null ? null : odt.toInstant();
    }

    default OffsetDateTime map(Instant instant) {
        return instant == null ? null : OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    default List<PaymentDto> toDtos(List<Payment> payments) {
        if (payments == null || payments.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return payments.stream()
                .filter(java.util.Objects::nonNull)
                .map(this::toDto)
                .collect(java.util.stream.Collectors.toList());
    }
}
