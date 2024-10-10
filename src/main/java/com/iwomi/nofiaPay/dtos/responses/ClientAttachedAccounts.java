package com.iwomi.nofiaPay.dtos.responses;


import lombok.Data;

@Data
public class ClientAttachedAccounts {
    private String accountTypeCode;
    private String branchCode;
    private  String accountNumber;
    private  String accountTypeLabel;
    private  String status;
    private  String clientCode;
}
