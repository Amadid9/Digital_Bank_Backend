package com.example.AppGestionBank.services;

import com.example.AppGestionBank.dto.*;
import com.example.AppGestionBank.entities.CompteBancaire;
import com.example.AppGestionBank.exceptions.CompteBancaireNotFoundException;
import com.example.AppGestionBank.exceptions.CompteSuspendedException;
import com.example.AppGestionBank.exceptions.MontantNotSufficientException;

import java.util.List;

public interface CompteService {

    CompteResponse createCompteCourant(CompteCourantRequest request);

    CompteResponse createCompteEpargne(CompteEpargneRequest request);

    CompteResponse getCompte(String id) throws CompteBancaireNotFoundException;
    List<CompteResponse> getAllComptes();
    List<CompteResponse> getComptesByClient(Long clientId);

    void deleteCompte(String id) throws CompteBancaireNotFoundException;

    OperationResponse crediter(OperationRequest request) throws CompteBancaireNotFoundException,CompteSuspendedException;

    OperationResponse debiter(OperationRequest request) throws CompteBancaireNotFoundException, MontantNotSufficientException,CompteSuspendedException;

    void virement(VirementRequest request) throws CompteBancaireNotFoundException, MontantNotSufficientException,CompteSuspendedException;

    List<OperationResponse> listOperations(String compteId);

    void suspendCompte(String compteId) throws CompteBancaireNotFoundException;

}
