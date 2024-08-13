package com.iwomi.nofiaPay.frameworks.generate;

public record TransactionFile(
        String accountNumber,
        String transactionLabel,
        String transactionReference,
        String debitedAmount,
        String creditedAmount,
        String referenceLettering
) {
}
