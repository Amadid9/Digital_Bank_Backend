package com.example.AppGestionBank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OperationRequest {

    @NotNull
    //private Long compteId;
    private String compteId;

    @NotNull
    private Double montant;

    private String description;
}