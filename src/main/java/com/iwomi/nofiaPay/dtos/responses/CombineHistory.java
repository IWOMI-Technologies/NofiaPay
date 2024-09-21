package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CombineHistory {
   private String name;
    private String service;
    private BigDecimal amount;
    private String phone;
    private String acc;
    private String senderAcc;
    private String branchName;
    private UUID transactionId;
    private Date transactionDate;
    private String status;
    private SenseTypeEnum sense;
}
