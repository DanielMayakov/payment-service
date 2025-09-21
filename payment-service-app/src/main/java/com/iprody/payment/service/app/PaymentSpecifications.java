package com.iprody.payment.service.app;

import com.iprody.payment.service.app.model.Payment;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;

public final class PaymentSpecifications {
    public static Specification<Payment> hasCurrency(String currency) {
        return (root, query, cb) -> cb.equal(root.get("currency"),
                currency);
    }

    public static Specification<Payment> amountBetween(BigDecimal min,
                                                       BigDecimal max) {
        return (root, query, cb) -> cb.between(root.get("amount"), min,
                max);
    }

    public static Specification<Payment> createdBetween(Instant after,
                                                        Instant before) {
        return (root, query, cb) -> cb.between(root.get("createdAt"),
                after, before);
    }

    public static Specification<Payment> fromFilter(PaymentFilter filter) {
        if (filter == null) {
            return null;
        }

        Specification<Payment> spec = null;

        if (filter.getCurrency() != null && !filter.getCurrency().isBlank()) {
            spec = Specification.where(hasCurrency(filter.getCurrency()));
        }

        BigDecimal min = filter.getMinAmount();
        BigDecimal max = filter.getMaxAmount();
        if (min != null || max != null) {
            Specification<Payment> amountSpec = (root, query, cb) -> {
                if (min != null && max != null) {
                    return cb.between(root.get("amount"), min, max);
                } else if (min != null) {
                    return cb.greaterThanOrEqualTo(root.get("amount"), min);
                } else {
                    return cb.lessThanOrEqualTo(root.get("amount"), max);
                }
            };
            spec = (spec == null) ? amountSpec : spec.and(amountSpec);
        }

        Instant after = filter.getCreatedAfter();
        Instant before = filter.getCreatedBefore();
        if (after != null || before != null) {
            Specification<Payment> createdSpec = (root, query, cb) -> {
                if (after != null && before != null) {
                    return cb.between(root.get("createdAt"), after, before);
                } else if (after != null) {
                    return cb.greaterThanOrEqualTo(root.get("createdAt"), after);
                } else {
                    return cb.lessThanOrEqualTo(root.get("createdAt"), before);
                }
            };
            spec = (spec == null) ? createdSpec : spec.and(createdSpec);
        }

        return spec;
    }

}