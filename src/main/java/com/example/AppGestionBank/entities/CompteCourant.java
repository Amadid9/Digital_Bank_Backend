package com.example.AppGestionBank.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@Setter
//@PrimaryKeyJoinColumn(name = "id_compteBancaire")
@DiscriminatorValue("CC")
public class CompteCourant extends CompteBancaire{
    //private Long id_compteCourant;
    private double decouvert;
}
