package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.core.enums.IdTypeEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Client {
    private UUID uuid;
    private String clientCode;
    private String firstName;
    private String lastName;
    private String fullName;
    private String agencyCode;
    private String agencyLabel;
    private String managerCode;
    private String managerName;
    private String firstAddress;
    private String secondAddress;
    private String clientType;

    private Date dateOfBirth;
    private String placeOfBirth;
    private String idNumber;

    private Date idDeliveryDate;
    private String idDeliveryPlace;
    private Date idExpirationDate;
    private String commercialRegNum;
    private String taxPayerNumber;
    private Date businessCreationDate;
    private String notificationPhoneNumber;
    private String phoneNumber;

    private Date clientCreationDate;
    private String email;
    private String agentCode;
    private String agentName;

    private List<Account> accounts;

}
