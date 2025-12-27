package com.example.AppGestionBank.services;

import com.example.AppGestionBank.dto.*;
import com.example.AppGestionBank.dto.CompteCourantRequest;
import com.example.AppGestionBank.entities.*;
import com.example.AppGestionBank.entities.enums.StatCompte;
import com.example.AppGestionBank.entities.enums.TypeOp;
import com.example.AppGestionBank.exceptions.CompteBancaireNotFoundException;
import com.example.AppGestionBank.exceptions.CompteSuspendedException;
import com.example.AppGestionBank.exceptions.MontantNotSufficientException;
import com.example.AppGestionBank.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
@Transactional
public class CompteServiceImpl implements CompteService {

    private final ClientRepository clientRepository;
    private final CompteBancaireRepository compteBancaireRepository;
    private final CompteCourantRepository compteCourantRepository;
    private final CompteEpargneRepository compteEpargneRepository;
    private final OperationRepository operationRepository;

    @Override
    public CompteResponse createCompteCourant(CompteCourantRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id " + request.getClientId()));

        /*if (compteBancaireRepository.existsById(request.getId())) {
            throw new CompteAlreadyExistsException("Ce compte existe déjà.");
        }*/

        com.example.AppGestionBank.entities.CompteCourant cc = new com.example.AppGestionBank.entities.CompteCourant();
        //cc.setId(request.getId());
        cc.setId(UUID.randomUUID().toString());
        cc.setClient(client);
        cc.setDateCreation(new Date());
        cc.setSolde(request.getSoldeInitial());
        cc.setDevise(request.getDevise());
        cc.setStatut(StatCompte.CREATED);
        cc.setDecouvert(request.getDecouvert());

        cc = compteCourantRepository.save(cc);
        return toResponse(cc);
    }

    @Override
    public CompteResponse createCompteEpargne(CompteEpargneRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id " + request.getClientId()));

        /*if (compteBancaireRepository.existsById(request.getId())) {
            throw new CompteAlreadyExistsException("Ce compte existe déjà.");
        }*/

        CompteEpargne ce = new CompteEpargne();
        //ce.setId(request.getId());
        ce.setId(UUID.randomUUID().toString());
        ce.setClient(client);
        ce.setDateCreation(new Date());
        ce.setSolde(request.getSoldeInitial());
        ce.setDevise(request.getDevise());
        ce.setStatut(StatCompte.CREATED);
        ce.setTauxInteret(request.getTauxInteret());

        ce = compteEpargneRepository.save(ce);
        return toResponse(ce);
    }

    @Override
    public CompteResponse getCompte(String id) {
        CompteBancaire cb = compteBancaireRepository.findById(id)
                .orElseThrow(() -> new CompteBancaireNotFoundException("Compte not found with id " + id));
        return toResponse(cb);
    }

    @Override
    public List<CompteResponse> getAllComptes() {
        return compteBancaireRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Override
    public List<CompteResponse> getComptesByClient(Long clientId) {
        return compteBancaireRepository.findByClientId(clientId)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Override
    public void deleteCompte(String compteId) {

        CompteBancaire compte = compteBancaireRepository.findById(compteId)
                .orElseThrow(() -> new CompteBancaireNotFoundException("Compte introuvable"));

        operationRepository.deleteByCompteBancaire_Id(compteId);

        compteBancaireRepository.delete(compte);
    }


    @Override
    public OperationResponse crediter(OperationRequest request) throws CompteSuspendedException,CompteBancaireNotFoundException {
        CompteBancaire compte = compteBancaireRepository.findById(request.getCompteId())
                .orElseThrow(() -> new CompteBancaireNotFoundException("Compte not found with id " + request.getCompteId()));

        if (compte.getStatut() == StatCompte.SUSPENDED) {
            throw new CompteSuspendedException("Compte suspendu");
        }
        compte.setSolde(compte.getSolde() + request.getMontant());

        if (compte.getStatut() == StatCompte.CREATED) {
            compte.setStatut(StatCompte.ACTIVATED);
        }
        Operation op = new Operation();
        op.setCompteBancaire(compte);
        op.setMontant(request.getMontant());
        op.setType(TypeOp.CREDIT);
        op.setDateOperation(new Date());
        op.setDescription(request.getDescription());

        op = operationRepository.save(op);
        return toResponse(op);
    }

    @Override
    public OperationResponse debiter(OperationRequest request)
            throws CompteBancaireNotFoundException, MontantNotSufficientException, CompteSuspendedException {

        CompteBancaire compte = compteBancaireRepository.findById(request.getCompteId())
                .orElseThrow(() ->
                        new CompteBancaireNotFoundException(
                                "Compte introuvable avec id " + request.getCompteId()
                        )
                );

        double montant = request.getMontant();
        double nouveauSolde = compte.getSolde() - montant;

        if (compte instanceof CompteEpargne) {
            if (nouveauSolde < 0) {
                throw new MontantNotSufficientException(
                        "Solde insuffisant pour un compte épargne"
                );
            }
        }

        if (compte instanceof com.example.AppGestionBank.entities.CompteCourant courant) {
            if (nouveauSolde < -courant.getDecouvert()) {
                throw new MontantNotSufficientException(
                        "Dépassement du découvert autorisé (" + courant.getDecouvert() + ")"
                );
            }
        }

        if (compte.getStatut() == StatCompte.SUSPENDED) {
            throw new CompteSuspendedException("Compte suspendu");
        }
        compte.setSolde(nouveauSolde);

        if (compte.getStatut() == StatCompte.CREATED) {
            compte.setStatut(StatCompte.ACTIVATED);
        }

        Operation op = new Operation();
        op.setCompteBancaire(compte);
        op.setMontant(montant);
        op.setType(TypeOp.DEBIT);
        op.setDateOperation(new Date());
        op.setDescription(request.getDescription());

        operationRepository.save(op);

        return toResponse(op);
    }

    @Override
    public void virement(VirementRequest request) throws CompteBancaireNotFoundException, MontantNotSufficientException, CompteSuspendedException {
        OperationRequest debitReq = new OperationRequest();
        debitReq.setCompteId(request.getSourceId());
        debitReq.setMontant(request.getMontant());
        debitReq.setDescription(request.getDescription());
        debiter(debitReq);

        OperationRequest creditReq = new OperationRequest();
        creditReq.setCompteId(request.getDestinationId());
        creditReq.setMontant(request.getMontant());
        creditReq.setDescription(request.getDescription()); //zadt had line
        crediter(creditReq);
    }

    @Override
    public List<OperationResponse> listOperations(String compteId) {
        return operationRepository.findByCompteBancaire_Id(compteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    private CompteResponse toResponse(CompteBancaire compte) {
        CompteResponse resp = new CompteResponse();
        resp.setId(compte.getId());
        resp.setDateCreation(compte.getDateCreation());
        resp.setSolde(compte.getSolde());
        resp.setDevise(compte.getDevise());
        resp.setStatut(
                compte.getStatut() != null ? compte.getStatut().name() : null
        );
        resp.setClientId(
                compte.getClient() != null ? compte.getClient().getId() : null
        );

        if (compte instanceof com.example.AppGestionBank.entities.CompteCourant) {
            resp.setType("COURANT");
            com.example.AppGestionBank.entities.CompteCourant courant = (com.example.AppGestionBank.entities.CompteCourant) compte;
            resp.setDecouvert(courant.getDecouvert());
        } else if (compte instanceof CompteEpargne) {
            resp.setType("EPARGNE");
            CompteEpargne epargne = (CompteEpargne) compte;
            resp.setTauxInteret(epargne.getTauxInteret());

        } else {
            resp.setType("UNKNOWN");
        }

        return resp;
    }

    private OperationResponse toResponse(Operation op) {
        OperationResponse resp = new OperationResponse();
        resp.setId(op.getId());
        resp.setDateOperation(op.getDateOperation());
        resp.setMontant(op.getMontant());
        resp.setType(op.getType() != null ? op.getType().name() : null);
        resp.setCompteId(
                op.getCompteBancaire() != null ? op.getCompteBancaire().getId() : null
        );
        resp.setDescription(op.getDescription());
        return resp;
    }

    @Override
    public void suspendCompte(String compteId) throws CompteBancaireNotFoundException {
        CompteBancaire compte = compteBancaireRepository.findById(compteId)
                .orElseThrow(() -> new CompteBancaireNotFoundException("Compte introuvable"));

        if (compte.getStatut() == StatCompte.SUSPENDED) {
            throw new IllegalStateException("Compte déjà suspendu");
        }

        compte.setStatut(StatCompte.SUSPENDED);
    }




}