package com.example.AppGestionBank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientRequest {

    @NotBlank
    private String nom;

    @Email
    @NotBlank
    private String email;

//    @NotBlank
//    private String password;
}
