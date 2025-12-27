package com.example.AppGestionBank.repositories;

import com.example.AppGestionBank.entities.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//Long id
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, String> {
    List<CompteBancaire> findByClientId(Long clientId); //Long id
}
