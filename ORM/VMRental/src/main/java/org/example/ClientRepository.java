package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientRepository extends AbstractMongoRepository {
    private final String collectionName = "clients";
    private final MongoCollection<Client> clients;


    public ClientRepository() {
        super.initDbConnection();
        MongoIterable<String> list = this.getDatabase().listCollectionNames();
//        for (String name : list) {
//            if (name.equals(collectionName)) {
//                this.getDatabase().getCollection(name).drop();
//                break;
//            }
//        }
//
//        this.getDatabase().createCollection(collectionName);

        this.clients = this.getDatabase().getCollection(collectionName, Client.class);
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void update(long id, Map<String, Object> fieldsToUpdate) {
//        // Check if there are fields to update
//        if (fieldsToUpdate == null || fieldsToUpdate.isEmpty()) {
//            throw new IllegalArgumentException("No fields to update.");
//        }
//        try (Session session = sessionFactory.openSession()) {
//            // Start the transaction
//            Transaction transaction = session.beginTransaction();
//
//            // Retrieve the entity by its ID
//            Object entity = session.get(Client.class, id);
//            if (entity == null) {
//                throw new IllegalArgumentException("Entity not found with id: " + id);
//            }
//
//            // Update the fields dynamically (use reflection)
//            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
//                String fieldName = entry.getKey();
//                Object fieldValue = entry.getValue();
//
//                // Use reflection to set the field value on the entity
//                try {
//                    Field field = Client.class.getDeclaredField(fieldName);
//                    field.setAccessible(true);
//                    field.set(entity, fieldValue);
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    throw new RuntimeException("Error updating field: " + fieldName, e);
//                }
//            }
//
//            // Save or update the entity
//            session.update(entity);
//
//            // Commit the transaction
//            transaction.commit();
//            System.out.println("Entity updated successfully.");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    public void add(Client client) {
        clients.insertOne(client);
    }

    public void remove(Client client) {
        Bson filter = Filters.eq("clientID", client.getEntityId());
        Client deletedClient = clients.findOneAndDelete(filter);
    }

    public long size() {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            Long count = (Long) session.createQuery("SELECT COUNT(c) FROM Client c").uniqueResult();
//            transaction.commit();
//            return count;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return 0;
    }

    public List<Client> getClients() {
        return clients.find().into(new ArrayList<Client>());
    }

    public Client getClientByID(long ID) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            Client client = session.createQuery("FROM Client c WHERE c.clientID = :clientID", Client.class)
//                    .setParameter("clientID", ID)
//                    .uniqueResult();
//
//            transaction.commit();
//            return client;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }


}
