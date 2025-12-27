package com.example.AppGestionBank.presentations.Controllers;

import com.example.AppGestionBank.dto.*;
import com.example.AppGestionBank.exceptions.CompteBancaireNotFoundException;
import com.example.AppGestionBank.exceptions.CompteSuspendedException;
import com.example.AppGestionBank.exceptions.MontantNotSufficientException;
import com.example.AppGestionBank.services.CompteService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
//@RequiredArgsConstructor
@AllArgsConstructor
public class CompteRestController {

    private final CompteService compteService;

    @PostMapping("/courant")
    public CompteResponse createCompteCourant(@RequestBody CompteCourantRequest request) {
        return compteService.createCompteCourant(request);
    }

    @PostMapping("/epargne")
    public CompteResponse createCompteEpargne(@RequestBody CompteEpargneRequest request) {
        return compteService.createCompteEpargne(request);
    }

    @GetMapping("/{id}")
    public CompteResponse getCompte(@PathVariable String id) {
        return compteService.getCompte(id);
    }

    @GetMapping
    public List<CompteResponse> getAllComptes() {
        return compteService.getAllComptes();
    }

    @GetMapping("/client/{clientId}")
    public List<CompteResponse> getComptesByClient(@PathVariable Long clientId) {
        return compteService.getComptesByClient(clientId);
    }

    @DeleteMapping("/{id}")
    public void deleteCompte(@PathVariable String id) {
        compteService.deleteCompte(id);
    }

    @PostMapping("/credit")
    public OperationResponse crediter(@RequestBody OperationRequest request) throws CompteSuspendedException {
        return compteService.crediter(request);
    }

    @PostMapping("/debit")
    public OperationResponse debiter(@RequestBody OperationRequest request)
            throws CompteBancaireNotFoundException,
                MontantNotSufficientException,
                CompteSuspendedException {
        return compteService.debiter(request);
    }

    @PostMapping("/virement")
    public void virement(@RequestBody VirementRequest request)
            throws CompteBancaireNotFoundException,
                MontantNotSufficientException,
                CompteSuspendedException {
        compteService.virement(request);
    }

    @GetMapping("/{compteId}/operations")
    public List<OperationResponse> listOperations(@PathVariable String compteId) {
        return compteService.listOperations(compteId);
    }

    @PutMapping("/{id}/suspend")
    public void suspendCompte(@PathVariable String id) {
        compteService.suspendCompte(id);
    }
}
