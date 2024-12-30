package com.xrm.tickly.ticketing_app.dto;

import com.xrm.tickly.ticketing_app.validation.StrongPassword;
import com.xrm.tickly.ticketing_app.validation.AllowedEmailDomain;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "{validation.notblank}")
    @Size(min = 3, max = 50, message = "{validation.size}")
    private String username;

    @NotBlank(message = "{validation.notblank}")
    @Email(message = "{validation.email}")
    @AllowedEmailDomain(allowedDomains = {"company.com", "example.com"}, 
                       message = "Only company.com and example.com email domains are allowed")
    private String email;

    @NotBlank(message = "{validation.notblank}")
    @StrongPassword(message = "{validation.password.strength}")
    private String password;
}