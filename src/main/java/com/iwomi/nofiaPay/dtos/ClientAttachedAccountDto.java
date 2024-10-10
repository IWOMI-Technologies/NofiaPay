package com.iwomi.nofiaPay.dtos;

public record ClientAttachedAccountDto(
        String accountTypeCode,
        String branchCode,
        String accountNumber,
        String accountTypeLabel,
        String clientCode
) {
}
