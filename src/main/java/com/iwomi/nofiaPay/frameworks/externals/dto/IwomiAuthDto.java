package com.iwomi.nofiaPay.frameworks.externals.dto;

import java.io.Serializable;

public record IwomiAuthDto (String username, String password) implements Serializable
{
    private static final long serialVersionUID = 5926468583005150707L;
}
