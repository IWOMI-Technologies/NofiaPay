package com.iwomi.nofiaPay.dtos;

public record PaymentProcessDto(
        String operation,
        String reason,
        String sourcePhoneNumber,
        String amount
) {
}
