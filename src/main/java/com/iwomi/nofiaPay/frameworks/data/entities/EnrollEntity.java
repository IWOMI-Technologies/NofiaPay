package com.iwomi.nofiaPay.frameworks.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "potential-client")
public class EnrollEntity extends BaseEntity {

    @Column(name = "first-name")
    private String firstName;
    @Column(name = "last-name")
    private String lastName;
    private String email;
    private String password;
    private String sex;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "identityCardNumber")
    private  String identityCardNumber;
    @Column(name = "address")
    private  String address;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "accountType")
    private  String accountType;

}
