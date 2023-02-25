package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class RegistrationRequest {
    @NotBlank
    @Size(min = 5, max = 15)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}
