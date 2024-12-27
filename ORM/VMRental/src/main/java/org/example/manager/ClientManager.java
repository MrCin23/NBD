package org.example.manager;


import org.example.model.Client;
import org.example.model.ClientType;
import org.example.repository.ClientRepository;

import java.util.Map;
import java.util.UUID;

//ClientManager jako Singleton
public final class ClientManager {
    private static ClientManager instance;
    private final ClientRepository clientsRepository;

    public ClientManager() {
        clientsRepository = new ClientRepository();
    }

    public static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public void registerExistingClient(Client client) {
        clientsRepository.add(client);
    }

    public void registerClient(String firstName, String surname, String emailAddress, ClientType clientType) {
        Client client = new Client(firstName, surname, emailAddress, clientType);
        registerExistingClient(client);
    }

    public void unregisterClient(Client client) {
        clientsRepository.remove(client);
    }

    public void updateField(UUID id, Map<String, Object> fieldsToUpdate) {
        clientsRepository.update(id, fieldsToUpdate);
    }

    public String getAllClientsReport() {
        return this.clientsRepository.getClients().toString();
    }
    public Client getClient(long clientID) {
        return (Client) clientsRepository.getClientByID(clientID);
    }

    public int getClientsAmount() {
        return clientsRepository.getClients().size();
    }
}

