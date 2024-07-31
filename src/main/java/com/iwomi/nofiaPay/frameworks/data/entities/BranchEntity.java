package com.iwomi.nofiaPay.frameworks.data.entities;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "branches")
@SQLDelete(sql = "UPDATE branches SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class BranchEntity extends BaseEntity {
    private String name;

    private boolean deleted = Boolean.FALSE;
}
