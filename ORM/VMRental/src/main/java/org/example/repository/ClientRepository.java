package org.example.repository;

import org.example.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientRepository {
    List<Client> clients;

    public ClientRepository() {
        clients = new ArrayList<Client>();
    }

    public void update(long id, Map<String, Object> fieldsToUpdate) {
        throw new RuntimeException("Not implemented yet");
    }

    public void add(Client client) {
        throw new RuntimeException("Not implemented yet");
    }

    public void remove(Client client) {
        throw new RuntimeException("Not implemented yet");
    }

    public long size() {
        throw new RuntimeException("Not implemented yet");
    }

    public List<Client> getClients() {
        throw new RuntimeException("Not implemented yet");
    }

    public Client getClientByID(long ID) {
        throw new RuntimeException("Not implemented yet");
    }
}
