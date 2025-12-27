package com.example.AppGestionBank.repositories;

import com.example.AppGestionBank.entities.CompteEpargne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteEpargneRepository extends JpaRepository<CompteEpargne, Long> {
}
