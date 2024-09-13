package com.iwomi.nofiaPay.dtos;


import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EnrollDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String sex;
    private String phoneNumber;
    private String identityCardNumber;
    private  String address;
    private String nationality;
    private  String accountType;

}
