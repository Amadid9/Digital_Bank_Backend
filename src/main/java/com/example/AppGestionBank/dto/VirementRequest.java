package com.example.AppGestionBank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VirementRequest {

    @NotNull
    //private Long sourceId;
    private String sourceId;

    @NotNull
    //private Long destinationId;
    private String destinationId;

    @NotNull
    private Double montant;
    private String description;
}
