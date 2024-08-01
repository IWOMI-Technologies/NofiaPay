package com.iwomi.nofiaPay.frameworks.data.entities;

import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "accounts-history")
public class AccountHistoryEntity extends  BaseEntity {

    private String name; 
    private String reason;
    private BigDecimal amount;

    @Column(name = "account-number")
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private SenseTypeEnum sense;



}
