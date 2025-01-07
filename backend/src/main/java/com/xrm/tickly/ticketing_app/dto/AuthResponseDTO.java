package com.xrm.tickly.ticketing_app.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private UserDTO user;

    public AuthResponseDTO(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }
}