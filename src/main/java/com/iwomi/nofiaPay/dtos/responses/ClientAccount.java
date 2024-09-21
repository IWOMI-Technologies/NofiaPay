package com.iwomi.nofiaPay.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAccount {
    private String name;
    private String accountNumber;
    private BigDecimal amount;
    private String phone;
    private String accountTypeLabel;
    private String branchName;
    private String branchCode;
    private Date openingDate;
    private String balance;
}
