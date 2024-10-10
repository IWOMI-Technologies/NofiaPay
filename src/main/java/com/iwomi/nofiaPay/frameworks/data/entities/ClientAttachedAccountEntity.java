package com.iwomi.nofiaPay.frameworks.data.entities;

import com.iwomi.nofiaPay.core.enums.StatusEnum;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "client_attached_accounts")
public class ClientAttachedAccountEntity extends BaseEntity {
    private String accountTypeCode;
    private String branchCode;
    private  String accountNumber;
    private  String accountTypeLabel;
    private StatusEnum status;
    private  String clientCode;
}
