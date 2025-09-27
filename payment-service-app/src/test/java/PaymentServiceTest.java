import com.iprody.payment.service.app.dto.PaymentDto;
import com.iprody.payment.service.app.mapper.PaymentMapper;
import com.iprody.payment.service.app.model.Payment;
import com.iprody.payment.service.app.model.PaymentStatus;
import com.iprody.payment.service.app.repository.PaymentRepository;
import com.iprody.payment.service.app.services.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;
    @InjectMocks
    private PaymentService paymentService;
    private Payment payment;
    private PaymentDto paymentDto;
    private UUID guid;

    @BeforeEach
    void setUp() {
        guid = UUID.randomUUID();

        payment = new Payment();
        payment.setGuid(guid);
        payment.setInquiryRefId(UUID.randomUUID());
        payment.setAmount(new BigDecimal("100.00"));
        payment.setCurrency("USD");
        payment.setStatus(PaymentStatus.APPROVED);
        payment.setCreatedAt(OffsetDateTime.now());
        payment.setUpdatedAt(OffsetDateTime.now());
        paymentDto = new PaymentDto();
        paymentDto.setGuid(payment.getGuid());
        paymentDto.setCurrency(payment.getCurrency());
        paymentDto.setStatus(payment.getStatus());
    }

    @Test
    void shouldReturnPaymentById() {
        when(paymentRepository.findById(guid)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);
        PaymentDto result = paymentService.get(guid);
        assertEquals(guid, result.getGuid());
        assertEquals("USD", result.getCurrency());
        assertEquals(PaymentStatus.APPROVED, result.getStatus());
        verify(paymentRepository).findById(guid);
        verify(paymentMapper).toDto(payment);
    }

    @ParameterizedTest
    @MethodSource("statusProvider")
    void shouldMapDifferentPaymentStatuses(PaymentStatus status) {
        payment.setStatus(status);
        paymentDto.setStatus(status);

        when(paymentRepository.findById(guid)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);
        PaymentDto result = paymentService.get(guid);
        assertEquals(status, result.getStatus());
        verify(paymentRepository).findById(guid);

        verify(paymentMapper).toDto(payment);
    }

    static Stream<PaymentStatus> statusProvider() {
        return Stream.of(
                PaymentStatus.RECEIVED,
                PaymentStatus.PENDING,
                PaymentStatus.APPROVED,
                PaymentStatus.DECLINED,
                PaymentStatus.NOT_SENT
        );
    }

    @Test
    void updateStatus_shouldUpdateAndReturnDto() {
        // given
        PaymentStatus newStatus = PaymentStatus.DECLINED;
        PaymentDto updatedDto = new PaymentDto();
        updatedDto.setGuid(guid);
        updatedDto.setCurrency(payment.getCurrency());
        updatedDto.setStatus(newStatus);

        when(paymentRepository.findById(guid)).thenReturn(Optional.of(payment));
        // возвращаем тот же объект, который передали в save (как будто JPA сохранила и вернула сущность)
        when(paymentRepository.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));
        when(paymentMapper.toDto(any(Payment.class))).thenReturn(updatedDto);

        // when
        PaymentDto result = paymentService.updateStatus(guid, newStatus);

        // then
        assertEquals(newStatus, result.getStatus());

        verify(paymentRepository).findById(guid);

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        assertEquals(newStatus, paymentCaptor.getValue().getStatus());

        verify(paymentMapper).toDto(any(Payment.class));
    }

    @Test
    void updateStatus_shouldNotCallSaveWhenStatusUnchanged() {
        // given
        PaymentStatus sameStatus = payment.getStatus(); // по setUp это APPROVED
        when(paymentRepository.findById(guid)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);

        // when
        PaymentDto result = paymentService.updateStatus(guid, sameStatus);

        // then
        assertEquals(sameStatus, result.getStatus());
        verify(paymentRepository).findById(guid);
        verify(paymentRepository, never()).save(any());
        verify(paymentMapper).toDto(payment);
    }

    @Test
    void updateStatus_shouldThrowWhenNewStatusNull() {
        // when/then
        assertThrows(IllegalArgumentException.class, () -> paymentService.updateStatus(guid, null));
        verifyNoInteractions(paymentRepository, paymentMapper);
    }

    @Test
    void updateStatus_shouldThrowWhenPaymentNotFound() {
        // given
        when(paymentRepository.findById(guid)).thenReturn(Optional.empty());

        // when/then
        assertThrows(EntityNotFoundException.class, () -> paymentService.updateStatus(guid, PaymentStatus.RECEIVED));
        verify(paymentRepository).findById(guid);
        verifyNoMoreInteractions(paymentRepository);
        verifyNoInteractions(paymentMapper);
    }

}