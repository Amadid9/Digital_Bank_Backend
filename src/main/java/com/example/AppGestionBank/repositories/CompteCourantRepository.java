package com.example.AppGestionBank.repositories;

import com.example.AppGestionBank.entities.CompteCourant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteCourantRepository extends JpaRepository<CompteCourant, Long> {
}
