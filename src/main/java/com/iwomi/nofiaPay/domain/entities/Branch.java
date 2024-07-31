package com.iwomi.nofiaPay.domain.entities;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Branch {
    private UUID uuid;
    private String name;
    private Date updatedAt;
    private Date createdAt;
}
