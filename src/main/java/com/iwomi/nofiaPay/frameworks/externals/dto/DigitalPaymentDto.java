package com.iwomi.nofiaPay.frameworks.externals.dto;

public record DigitalPaymentDto(
        String op_type,
        String type,
        String amount,
        String external_id,
        String motif,
        String tel,
        String country,
        String currency
) {
}
