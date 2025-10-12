package com.iprody.payment.service.app.mapper;

import com.iprody.payment.service.app.dto.PaymentDto;
import com.iprody.payment.service.app.model.Payment;
import com.iprody.payment.service.app.model.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentMapperTest {

    private final PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void shouldMapToDto() {
        // given
        UUID id = UUID.randomUUID();

        Payment payment = new Payment();
        payment.setId(id);
        payment.setAmount(new BigDecimal("123.45"));
        payment.setCurrency("USD");
        payment.setInquiryRefId(UUID.randomUUID());
        payment.setStatus(PaymentStatus.APPROVED);
        payment.setCreatedAt(OffsetDateTime.now());
        payment.setUpdatedAt(OffsetDateTime.now());

        // when
        PaymentDto dto = mapper.toDto(payment);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(payment.getId());
        // сравнение BigDecimal без учёта scale
        assertThat(dto.getAmount()).isEqualByComparingTo(payment.getAmount());
        assertThat(dto.getCurrency()).isEqualTo(payment.getCurrency());
        assertThat(dto.getInquiryRefId()).isEqualTo(payment.getInquiryRefId());
        assertThat(dto.getStatus()).isEqualTo(payment.getStatus());
        assertThat(dto.getCreatedAt()).isEqualTo(payment.getCreatedAt().toInstant());
        assertThat(dto.getUpdatedAt()).isEqualTo(payment.getUpdatedAt().toInstant());
    }

    @Test
    void shouldMapToEntity() {
// given
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();
        PaymentDto dto = PaymentDto.builder()
                .id(id)
                .amount(new BigDecimal("999.99"))
                .currency("EUR")
                .inquiryRefId(UUID.randomUUID())
                .status(PaymentStatus.PENDING)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();


        // when
        Payment entity = mapper.toEntity(dto);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.id());
        assertThat(entity.getAmount()).isEqualByComparingTo(dto.getAmount());
        assertThat(entity.getCurrency()).isEqualTo(dto.getCurrency());
        assertThat(entity.getInquiryRefId()).isEqualTo(dto.getInquiryRefId());
        assertThat(entity.getStatus()).isEqualTo(dto.getStatus());
        assertThat(entity.getCreatedAt().toInstant()).isEqualTo(dto.getCreatedAt());
        assertThat(entity.getUpdatedAt().toInstant()).isEqualTo(dto.getUpdatedAt());
    }

    @Test
    void shouldRoundTripEntityDto() {
        // given
        UUID id = UUID.randomUUID();
        OffsetDateTime createdAt = OffsetDateTime.now().minusDays(2);
        OffsetDateTime updatedAt = createdAt.plusHours(5);

        Payment original = new Payment();
        original.setId(id);
        original.setAmount(new BigDecimal("10.00")); // проверка на scale
        original.setCurrency("GBP");
        original.setInquiryRefId(UUID.randomUUID());
        original.setStatus(PaymentStatus.DECLINED);
        original.setCreatedAt(createdAt);
        original.setUpdatedAt(updatedAt);

        // when
        PaymentDto dto = mapper.toDto(original);
        Payment mappedBack = mapper.toEntity(dto);

        // then
        assertThat(mappedBack).isNotNull();
        assertThat(mappedBack.getId()).isEqualTo(original.getId());
        assertThat(mappedBack.getAmount()).isEqualByComparingTo(original.getAmount());
        assertThat(mappedBack.getCurrency()).isEqualTo(original.getCurrency());
        assertThat(mappedBack.getInquiryRefId()).isEqualTo(original.getInquiryRefId());
        assertThat(mappedBack.getStatus()).isEqualTo(original.getStatus());
        assertThat(mappedBack.getCreatedAt()).isEqualTo(original.getCreatedAt());
        assertThat(mappedBack.getUpdatedAt()).isEqualTo(original.getUpdatedAt());
    }

    @Test
    void shouldHandleNulls() {
        // Если в маппере задано RETURN_NULL (MapStruct):
        // @Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
        assertThat(mapper.toDto(null)).isNull();
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void shouldMapCollections() {
        // Опционально: если у маппера есть методы для коллекций
        Payment p1 = new Payment();
        p1.setId(UUID.randomUUID());
        p1.setAmount(new BigDecimal("1.23"));
        p1.setCurrency("USD");
        p1.setInquiryRefId(UUID.randomUUID());
        p1.setStatus(PaymentStatus.PENDING);
        p1.setCreatedAt(OffsetDateTime.now().minusDays(3));
        p1.setUpdatedAt(OffsetDateTime.now().minusDays(2));

        Payment p2 = new Payment();
        p2.setId(UUID.randomUUID());
        p2.setAmount(new BigDecimal("4.56"));
        p2.setCurrency("EUR");
        p2.setInquiryRefId(UUID.randomUUID());
        p2.setStatus(PaymentStatus.APPROVED);
        p2.setCreatedAt(OffsetDateTime.now().minusDays(1));
        p2.setUpdatedAt(OffsetDateTime.now());

        List<PaymentDto> dtos = mapper.toDtos(List.of(p1, p2));

        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).id()).isEqualTo(p1.getId());
        assertThat(dtos.get(1).id()).isEqualTo(p2.getId());
    }
}
