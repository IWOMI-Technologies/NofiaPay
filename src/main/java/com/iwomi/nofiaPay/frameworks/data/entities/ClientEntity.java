package com.iwomi.nofiaPay.frameworks.data.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iwomi.nofiaPay.core.constants.AppConst;
import com.iwomi.nofiaPay.core.enums.IdTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "clients")
public class ClientEntity extends BaseEntity  {

    private String clientCode;  //code unique code
    private String firstName;
    private String lastName;
    private String fullName;
    private String agencyCode;
    private String agencyLabel;
    private String managerCode; // code gestionnaire
    private String managerName; // nom du gestionnaire
    private String firstAddress;
    private String secondAddress;
    private String clientType;  // PP or Person morale
    private String agentCode;
    private String agentName;

    private String phoneNumber; //used for connection
    private String notificationPhoneNumber; //where to send notifications (sms)
    private String email;   //email du client
    private String idNumber;  // from CNI, PASSPORT, etc
    private String idDeliveryPlace;
    private String idDeliveryAuthority;
    private String placeOfBirth;
    private String commercialRegNum;
    private String taxPayerNumber;
    private Date businessCreationDate;

    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    private Date clientCreationDate;
    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    private Date idDeliveryDate;
    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    private Date idExpirationDate;
    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

}
