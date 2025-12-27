package com.example.AppGestionBank.repositories;

import com.example.AppGestionBank.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByCompteBancaire_Id(String compteId); //Long
    void deleteByCompteBancaire_Id(String compteId); //Long

}
