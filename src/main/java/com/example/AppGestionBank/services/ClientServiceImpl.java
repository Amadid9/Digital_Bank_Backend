package com.example.AppGestionBank.services;

import com.example.AppGestionBank.dto.ClientRequest;
import com.example.AppGestionBank.dto.ClientResponse;
import com.example.AppGestionBank.entities.Client;
import com.example.AppGestionBank.exceptions.ClientNotFoundException;
import com.example.AppGestionBank.repositories.ClientRepository;
import com.example.AppGestionBank.repositories.CompteBancaireRepository;
import com.example.AppGestionBank.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final CompteBancaireRepository compteBancaireRepository;
    private final OperationRepository operationRepository;

    @Override
    public ClientResponse createClient(ClientRequest request) {
        Client client = Client.builder()
                .nom(request.getNom())
                .email(request.getEmail())
//                .password(request.getPassword())
                .build();

        return toResponse(clientRepository.save(client));
    }

    @Override
    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ClientResponse getClientById(Long id) throws ClientNotFoundException {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id " + id));
        return toResponse(client);
    }

    @Override
    public void deleteClient(Long id) throws ClientNotFoundException {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        client.getComptes().forEach(compte ->
                operationRepository.deleteByCompteBancaire_Id(compte.getId())
        );

        compteBancaireRepository.deleteAll(client.getComptes());
        clientRepository.delete(client);
    }

    @Override
    public ClientResponse updateClient(Long id, ClientRequest request) throws ClientNotFoundException {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id " + id));

        existing.setNom(request.getNom());
        existing.setEmail(request.getEmail());
//        existing.setPassword(request.getPassword());

        return toResponse(clientRepository.save(existing));
    }

    private ClientResponse toResponse(Client client) {
        ClientResponse resp = new ClientResponse();
        resp.setId(client.getId());
        resp.setNom(client.getNom());
        resp.setEmail(client.getEmail());
        return resp;
    }

    @Override
    public List<ClientResponse> searchClients(String keyword) {
        return clientRepository
                .findByNomContainingIgnoreCase(keyword)
                .stream()
                .map(this::toResponse)
                .toList();
    }



}
