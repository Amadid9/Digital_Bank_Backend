package com.example.AppGestionBank.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OperationResponse {
    private Long id;
    private Date dateOperation;
    private double montant;
    private String type;
    //private Long compteId;
    private String compteId;
    private String description;
}