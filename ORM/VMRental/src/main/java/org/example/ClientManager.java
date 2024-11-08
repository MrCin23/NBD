package org.example;


import java.util.List;
import java.util.Map;

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

    public void updateField(long id, Map<String, Object> fieldsToUpdate) {
        clientsRepository.update(id, fieldsToUpdate);
    }

    //METHODS-----------------------------------
//TODO
    public String getAllClientsReport() {
        return this.clientsRepository.getClients().toString();
    }
    public List<Client> getAllClients() {
        return this.clientsRepository.getClients();
    }
    public Client getClient(long clientID) {
        return (Client) clientsRepository.getClientByID(clientID);
    }

    public int getClientsAmount() {
        return clientsRepository.getClients().size();
    }
}

