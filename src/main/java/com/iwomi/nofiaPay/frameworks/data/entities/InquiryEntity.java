package com.iwomi.nofiaPay.frameworks.data.entities;


import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "inquires")
public class InquiryEntity extends BaseEntity {
    private String name;
    private String email;
    private String content;
}
