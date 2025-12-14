package com.techlab.NookBooks.controller;

import com.techlab.NookBooks.service.ClientService;
import com.techlab.NookBooks.model.entity.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clientService.createClient(client));
    }

    // GET /client?clienName="name"
    @GetMapping
    public ResponseEntity<List<Client>> showClients(
            @RequestParam(required = false, defaultValue = "") String clientName){
        return ResponseEntity.status(HttpStatus.OK).body(this.clientService.showClients(clientName));
    }
    @GetMapping("/{clientId}")
    public ResponseEntity<Client> showClient (@PathVariable Long clientId){
        return ResponseEntity.status(HttpStatus.OK).body(this.clientService.searchClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> editClient(@PathVariable Long id, @RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.clientService.editClient(id, client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable(name="id") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(this.clientService.deleteClient(categoryId));
    }
}
