package com.iwomi.nofiaPay.frameworks.data.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "validators")
public class ValidatorEntity extends BaseEntity {
    private String process;
    private Set<String> profiles;
    private Set<String> users;
}
