package com.example.AppGestionBank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompteCourantRequest {
    //private Long id;
    //private String id;
    @NotNull
    private Long clientId;

    @NotNull
    private Double soldeInitial;

    @NotNull
    private Double decouvert;

    //private String devise = "MAD";
    private String devise;
}
