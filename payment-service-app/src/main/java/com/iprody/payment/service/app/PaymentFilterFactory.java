package com.iprody.payment.service.app;

import com.iprody.payment.service.app.model.Payment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class PaymentFilterFactory {
    public static Specification<Payment> fromFilter(PaymentFilter
                                                            filter) {
        Specification<Payment> spec = Specification.unrestricted();
        if (StringUtils.hasText(filter.getCurrency())) {
            spec =

                    spec.and(PaymentSpecifications.hasCurrency(filter.getCurrency()));
        }
        if (filter.getMinAmount() != null && filter.getMaxAmount() !=
                null) {

            spec = spec.and(PaymentSpecifications.amountBetween(
                    filter.getMinAmount(), filter.getMaxAmount()));

        }
        if (filter.getCreatedAfter() != null &&
                filter.getCreatedBefore() != null) {

            spec = spec.and(PaymentSpecifications.createdBetween(
                    filter.getCreatedAfter(),

                    filter.getCreatedBefore()));
        }
        return spec;
    }
}