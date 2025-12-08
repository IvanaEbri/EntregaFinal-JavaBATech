package com.techlab.client;

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
        if (client.getClientName().isEmpty()){
            System.out.println("Debe ingresar un nombre valido para el cliente");
            return null;
        }
        if (client.getAddress().isEmpty()){
            System.out.println("Debe ingresar una direccion valida para el cliente");
        }
        this.clientRepository.save(client);
        return client;
    }

    public List<Client> showClients (String clientName){
        if (!clientName.isEmpty()) {
            return this.clientRepository.findByActiveTrueAndClientNameContaining(clientName);
        }
        return this.clientRepository.findAll();
    }

    public Client searchClient (Long id){
        return this.clientRepository.findByActiveTrueAndId(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el cliente"));
    }

    public Client editClient (Long id, Client dataClient){
        Client client = this.clientRepository.findByActiveTrueAndId(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el cliente"));

        if (dataClient.getClientName().isEmpty()){
            System.out.println("Debe ingresar un nombre valido para el cliente");
            return null;
        }
        if (dataClient.getAddress().isEmpty()){
            System.out.println("Debe ingresar una direccion valida para el cliente");
        }

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
}
