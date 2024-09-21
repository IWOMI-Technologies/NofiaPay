package com.iwomi.nofiaPay.dtos.responses;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Data
public class Account {
    private String agencyCode;
    private String agencyName;
    private String currency;
    private String cle;
    private String accountTitle;
    private String chapter;
    private String chapterTitle;
    private String accountTypeCode;
    private String accountTypeLabel;
    private String accountNumber;
    private BigDecimal balance;
    private String clientCode;
}
