package com.iwomi.nofiaPay.frameworks.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "batches")
@SQLDelete(sql = "UPDATE batches SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class BatchEntity extends BaseEntity {
    @Column(name = "branch_code") private String batchCode;
    @Column(name = "client_id") private String clientId;

    // amount remaining after reversement
    // if 0 then batch is fully paid
    @Column(nullable = false) private BigDecimal remainder;
}
