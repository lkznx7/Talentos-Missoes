package com.lkznx7.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterUser(@Email @NotBlank String username,  @NotBlank @NotNull @Size(min = 6,max = 20) String password) {
}
