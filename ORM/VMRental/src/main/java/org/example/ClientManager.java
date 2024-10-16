package org.example;


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

    public void registerExsistingClient(Client client) {
        clientsRepository.add(client);
    }

    public void registerClient(String firstName, String surname, String emailAddress, ClientType clientType) {
        Client client = new Client(firstName, surname, emailAddress, clientType);
        registerExsistingClient(client);
    }

    public void unregisterClient(Client client) {
        clientsRepository.remove(client);
    }

    //METHODS-----------------------------------
//TODO
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

