package com.iwomi.nofiaPay.dtos.response;

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
