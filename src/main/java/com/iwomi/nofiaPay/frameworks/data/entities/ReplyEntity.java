package com.iwomi.nofiaPay.frameworks.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "replies")
public class ReplyEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "inquiry_uuid")
    private InquiryEntity inquiry;

    private  String reply;
}
