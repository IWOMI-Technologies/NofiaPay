package com.iwomi.nofiaPay.dtos.responses;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Inquiry {
    private UUID uuid;
    private String name;
    private String email;
    private String content;
    private Date updatedAt;
    private Date createdAt;
}
