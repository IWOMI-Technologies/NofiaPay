package com.iwomi.nofiaPay.frameworks.data.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.iwomi.nofiaPay.core.constants.AppConst;
import com.iwomi.nofiaPay.core.enums.AccountStatusEnum;
import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "accounts")
public class AccountEntity extends BaseEntity {

    private String agencyCode;
    private String agencyName;
    private String currency;
    private String cle;
    private String accountTitle;
    private String chapter;
    private String chapterTitle;
    private String accountTypeCode;
    private String accountTypeLabel;

    @Column(name = "account_number") private String accountNumber;
    @Column(name = "balance") private BigDecimal balance;
    @Column(name = "client_code") private String clientCode;

    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    private Date accountCreation;


//    @Enumerated(EnumType.STRING) private AccountStatusEnum accountStatus;
//    @Enumerated(EnumType.STRING) private AccountTypeEnum type;
}
