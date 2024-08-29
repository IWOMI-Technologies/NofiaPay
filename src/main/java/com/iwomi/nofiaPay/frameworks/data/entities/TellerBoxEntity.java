package com.iwomi.nofiaPay.frameworks.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "teller-boxes")
@SQLDelete(sql = "UPDATE teller-boxes SET deleted = true WHERE uuid=?")
@Where(clause = "deleted=false")
public class TellerBoxEntity extends BaseEntity {
    private String number;
    @Column(name = "branch_code") private String branchCode;
    @Column(name = "client_code")  private String clientCode;

    private boolean deleted = Boolean.FALSE;
}