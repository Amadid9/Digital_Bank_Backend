package com.example.AppGestionBank.presentations.Controllers;

import com.example.AppGestionBank.dto.ClientRequest;
import com.example.AppGestionBank.dto.ClientResponse;
import com.example.AppGestionBank.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
@CrossOrigin("*")
public class ClientRestController {

    private final ClientService clientService;

    @PostMapping
    public ClientResponse createClient(@RequestBody ClientRequest request) {
        return clientService.createClient(request);
    }

    @GetMapping
    public List<ClientResponse> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientResponse getClient(@PathVariable Long id){
        return clientService.getClientById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable Long id, @RequestBody ClientRequest request) {
        return clientService.updateClient(id, request);
    }

    @GetMapping("/search")
    public List<ClientResponse> searchClients(@RequestParam String keyword) {
        return clientService.searchClients(keyword);
    }



}