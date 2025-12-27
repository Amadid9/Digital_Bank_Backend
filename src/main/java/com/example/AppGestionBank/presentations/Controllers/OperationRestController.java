package com.example.AppGestionBank.presentations.Controllers;

import com.example.AppGestionBank.dto.CompteResponse;
import com.example.AppGestionBank.dto.OperationRequest;
import com.example.AppGestionBank.dto.OperationResponse;
import com.example.AppGestionBank.dto.VirementRequest;
import com.example.AppGestionBank.entities.CompteBancaire;
import com.example.AppGestionBank.entities.Operation;
import com.example.AppGestionBank.exceptions.CompteBancaireNotFoundException;
import com.example.AppGestionBank.exceptions.CompteSuspendedException;
import com.example.AppGestionBank.exceptions.MontantNotSufficientException;
import com.example.AppGestionBank.repositories.OperationRepository;
import com.example.AppGestionBank.services.CompteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
@AllArgsConstructor
public class OperationRestController {

    private final CompteService compteService;

    @PostMapping("/credit")
    public OperationResponse credit(@RequestBody OperationRequest request) throws CompteSuspendedException{
        return compteService.crediter(request);
    }

    @PostMapping("/debit")
    public OperationResponse debit(@RequestBody OperationRequest request) throws CompteBancaireNotFoundException, MontantNotSufficientException, CompteSuspendedException {
        return compteService.debiter(request);
    }

    @PostMapping("/virement")
    public void virement(@RequestBody VirementRequest request) throws CompteBancaireNotFoundException, MontantNotSufficientException, CompteSuspendedException {
        compteService.virement(request);
    }

    @GetMapping("/compte/{id}")
    public List<OperationResponse> getOperations(@PathVariable String id) {
        return compteService.listOperations(id);
    }

}
