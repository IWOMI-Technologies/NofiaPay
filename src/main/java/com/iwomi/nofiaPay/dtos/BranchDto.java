package com.iwomi.nofiaPay.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BranchDto(
        @NotNull(message = "branch name is mandatory")
        @NotBlank(message = "branch name is mandatory")
        @Size(min = 4, max = 14, message = "branch name must be between 3 to 14 characters")
        String name,
        String code
) {
}
