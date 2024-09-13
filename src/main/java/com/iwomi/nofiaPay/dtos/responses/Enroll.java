package com.iwomi.nofiaPay.dtos.responses;

import java.util.UUID;

public record Enroll(
        UUID uuid,
        String email,
        String password,
        String sex,
        String phoneNumber,
        String identityCardNumber,
         String address,
        String nationality,
        String accountType
) {
}
