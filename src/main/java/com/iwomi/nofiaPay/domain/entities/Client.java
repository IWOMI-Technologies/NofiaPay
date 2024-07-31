package com.iwomi.nofiaPay.domain.entities;

import com.iwomi.nofiaPay.core.enums.IdTypeEnum;
import lombok.Data;

import java.util.Date;

@Data
public class Client {
    private String institutionCode; //code etablissement
    private String branchId;

    private String matricule;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String idNumber;
    private IdTypeEnum idType; // enum
    private String idDeliveryPlace;
    private String idDeliveryAuthority;
    private String placeOfBirth;
    private String commercialRegNum;
    private String taxPayerNumber;
    private Date businessCreationDate;
    private String address;

    private Date idDeliveryDate;
    private Date idExpirationDate;
    private Date dateOfBirth;
}
