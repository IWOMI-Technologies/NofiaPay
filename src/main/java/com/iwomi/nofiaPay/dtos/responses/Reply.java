package com.iwomi.nofiaPay.dtos.responses;

import lombok.Data;

import java.util.Date;

@Data
public class Reply {
    private  String reply;
    private Date updatedAt;
    private Date createdAt;
}
