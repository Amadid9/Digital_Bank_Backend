package com.example.AppGestionBank.dto;

import com.example.AppGestionBank.entities.enums.TypeOp;
import lombok.Data;

import java.util.Date;

@Data
public class CompteResponse {

    //private Long id;
    private String id;
    private Date dateCreation;
    private String type;
    private double solde;
    private String statut;
    private String devise;
    private Long clientId;


    private Double decouvert;
    private Double tauxInteret;
}