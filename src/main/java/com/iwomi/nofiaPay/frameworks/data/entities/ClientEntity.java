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
@SQLDelete(sql = "UPDATE clients SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class ClientEntity extends BaseEntity  {

    private String institutionCode; //code etablissement
    @Column(name = "branch_id") private String branchId;

    private String clientCode;//code unique code
    private String phoneNumber;//tel cl pr connexion
    private String email;//email du client
    private String firstName;
    private String lastName;
    private String idNumber;    // from CNI, PASSPORT, etc
    @Enumerated(EnumType.STRING)
    private IdTypeEnum idType; // enum
    private String idDeliveryPlace;
    private String idDeliveryAuthority;
    private String placeOfBirth;
    private String commercialRegNum;
    private String taxPayerNumber;
    private Date businessCreationDate;
    private String address;

    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    private Date idDeliveryDate;
    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    private Date idExpirationDate;
    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private boolean deleted = Boolean.FALSE;
}
