package com.example.AppGestionBank.entities;

import com.example.AppGestionBank.entities.enums.TypeOp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOperation;
    private double montant;
    @Enumerated(EnumType.STRING)
    private TypeOp type;
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "compte_id")
    @JsonIgnore // bach nhalo lmochkil dyal json f postman makayw9afch (boucle infinit)
    private CompteBancaire compteBancaire;
    private String description;
}
