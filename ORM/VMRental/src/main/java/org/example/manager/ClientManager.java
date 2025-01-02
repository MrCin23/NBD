package org.example.manager;

import org.example.DBConnection;
import org.example.dao.ClientDao;
import org.example.mapper.*;
import org.example.model.Client;
import org.example.model.ClientType;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

public class ClientManager {
    private static ClientManager instance;
    DBConnection db;
    ClientDao clientDao;

    public static synchronized ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    private ClientManager() {
        db = DBConnection.getInstance();
        db.initSession();
        db.createClientTable();
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        clientDao = clientMapper.clientDao();
    }

    public void registerClient(String firstName, String surname, String emailAddress, ClientType clientType) {
        Client client = new Client(firstName, surname, emailAddress, clientType);
        registerExistingClient(client);
    }

    public void registerExistingClient(Client client) {
        clientDao.create(client);
    }

    public void unregisterClient(Client client) {
        clientDao.delete(client);
    }

    public Client getClient(UUID uuid) {
        return clientDao.findById(uuid);
    }

    public void update(UUID uuid, Map<String, Object> fieldsToUpdate) {
        Client client = clientDao.findById(uuid);

        // Update the fields dynamically (use reflection)
        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            // Use reflection to set the field value on the entity
            try {
                Field field = Client.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(client, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error updating field: " + fieldName, e);
            }
        }

        clientDao.update(client);
    }

    public void update(UUID uuid, String field, Object value) {
        Client client = clientDao.findById(uuid);

        // Use reflection to set the field value on the entity
        try {
            Field fieldName = Client.class.getDeclaredField(field);
            fieldName.setAccessible(true);
            fieldName.set(client, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error updating field: " + field, e);
        }
        clientDao.update(client);
    }
}
