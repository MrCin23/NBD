package org.example;

import java.util.UUID;

//ClientManager jako Singleton
public final class ClientManager {
    private static ClientManager instance;
    Repository clientsRepository;

    public ClientManager() {
        clientsRepository = new Repository();
    }

    public static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public void registerExsistingClient(Client client) {
        clientsRepository.add(client);
    }

    public void registerClient(UUID clientID, String firstName, String surname, String emailAddress, ClientType clientType) {
        Client client = new Client(clientID, firstName, surname, emailAddress, clientType);
        registerExsistingClient(client);
    }

    public void unregisterClient(Client client) {
        clientsRepository.remove(client);
    }

    //METHODS-----------------------------------
//TODO
    public String getAllClientsReport() {
        return this.clientsRepository.getElements().toString();
    }
    public Client getClient(UUID clientID) {
        return clientsRepository.getElementByID(clientID);
    }
}

