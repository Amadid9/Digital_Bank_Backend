package com.example.AppGestionBank.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
@Builder
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;
    /*@NotBlank
    private String password;*/
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    //@JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private List<CompteBancaire> comptes;
}
