package com.iwomi.nofiaPay.dtos.responses;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Branch {
    private UUID uuid;
    private String name;
    private String code;
    private Date updatedAt;
    private Date createdAt;
}
