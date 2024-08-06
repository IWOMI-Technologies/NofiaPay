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
@SQLDelete(sql = "UPDATE teller-boxes SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class TellerBoxEntity extends BaseEntity {
    private String number;
    @Column(name = "branch_id") private String branchId;
    @Column(name = "client_id")  private String clientId;

    private boolean deleted = Boolean.FALSE;
}