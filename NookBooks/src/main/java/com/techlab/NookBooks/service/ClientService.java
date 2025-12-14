package com.techlab.NookBooks.service;

import com.techlab.NookBooks.model.entity.Client;
import com.techlab.NookBooks.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient (Client client){
        validateClient(client);
        return clientRepository.save(client);
    }

    public List<Client> showClients (String clientName){
        if (!clientName.isEmpty()) {
            return this.clientRepository.findByActiveTrueAndClientNameContaining(clientName);
        }
        return this.clientRepository.findByActiveTrue();
    }

    public Client searchClient (Long id){
        return this.clientRepository.findByActiveTrueAndId(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el cliente"));
    }

    public Client editClient (Long id, Client dataClient){
        validateClient(dataClient);
        Client client = this.clientRepository.findByActiveTrueAndId(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el cliente"));

        client.setClientName(dataClient.getClientName());
        client.setAddress(dataClient.getAddress());
        this.clientRepository.save(client);
        return client;
    }

    public Client deleteClient (Long id){
        Optional<Client> clientOptional = this.clientRepository.findByActiveTrueAndId(id);

        if (clientOptional.isEmpty()){
            System.out.println("No se puede eliminar el cliente ya que no se encuentra.");
            return null;
        }

        Client client = clientOptional.get();
        client.setActive(Boolean.FALSE);
        this.clientRepository.save(client);
        return client;
    }


    // ----- VALIDADOR -----
    private void validateClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (client.getClientName() == null || client.getClientName().isBlank()) {
            throw new IllegalArgumentException("Debe ingresar un nombre válido");
        }

        if (client.getAddress() == null || client.getAddress().isBlank()) {
            throw new IllegalArgumentException("Debe ingresar una dirección válida");
        }
    }
}
