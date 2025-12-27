package com.example.AppGestionBank.services;

import com.example.AppGestionBank.dto.ClientRequest;
import com.example.AppGestionBank.dto.ClientResponse;
import com.example.AppGestionBank.exceptions.ClientNotFoundException;

import java.util.List;

public interface ClientService {

    ClientResponse createClient(ClientRequest request);

    List<ClientResponse> getAllClients();

    ClientResponse getClientById(Long id)  throws ClientNotFoundException;

    void deleteClient(Long id);

    ClientResponse updateClient(Long id, ClientRequest request)  throws ClientNotFoundException;

    List<ClientResponse> searchClients(String keyword)  throws ClientNotFoundException;
}

