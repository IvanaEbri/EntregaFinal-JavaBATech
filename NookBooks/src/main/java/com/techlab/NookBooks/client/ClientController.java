package com.techlab.NookBooks.client;

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
    public Client createClient(@RequestBody Client client) {
        return this.clientService.createClient(client);
    }

    // GET /client?clienName="name"
    @GetMapping
    public List<Client> showClients(
            @RequestParam(required = false, defaultValue = "") String clientName){
        return this.clientService.showClients(clientName);
    }
    @GetMapping("/{clientId}")
    public Client showClient (@PathVariable Long clientId){
        return this.clientService.searchClient(clientId);
    }

    @PutMapping("/{id}")
    public Client editClient(@PathVariable Long id, @RequestBody Client client) {
        return this.clientService.editClient(id, client);
    }

    @DeleteMapping("/{id}")
    public Client deleteClient(@PathVariable(name="id") Long categoryId){
        return this.clientService.deleteClient(categoryId);
    }
}
