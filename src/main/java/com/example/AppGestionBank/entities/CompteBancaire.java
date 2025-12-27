package com.example.AppGestionBank.entities;

import com.example.AppGestionBank.entities.enums.StatCompte;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
//@Getter
//@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
public abstract class CompteBancaire {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    //private Long id;
    private double solde;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Enumerated(EnumType.STRING)
    private StatCompte statut;
    private String devise;
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "client_id")
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Client client;
    @OneToMany(mappedBy = "compteBancaire", fetch = FetchType.LAZY)
    private List<Operation> operation;

    //@Column(name = "TYPE", insertable = false, updatable = false)
    //private String type;
}
