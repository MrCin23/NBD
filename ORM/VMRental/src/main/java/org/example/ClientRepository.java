package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientRepository {
    List<Client> clients;
    private final String url = "jdbc:postgresql://localhost:5432/vmrental";
    private final String user = "postgres";
    private final String password = "password";
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();


    public ClientRepository() {
        clients = new ArrayList<Client>();
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void update(long id, Map<String, Object> fieldsToUpdate) {
        // Check if there are fields to update
        if (fieldsToUpdate == null || fieldsToUpdate.isEmpty()) {
            throw new IllegalArgumentException("No fields to update.");
        }
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // Start the transaction
            transaction = session.beginTransaction();

            // Retrieve the entity by its ID
            Object entity = session.get(Client.class, id);
            if (entity == null) {
                throw new IllegalArgumentException("Entity not found with id: " + id);
            }

            // Update the fields dynamically (use reflection)
            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                // Use reflection to set the field value on the entity
                try {
                    Field field = Client.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(entity, fieldValue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException("Error updating field: " + fieldName, e);
                }
            }

            // Save or update the entity
            session.update(entity);

            // Commit the transaction
            transaction.commit();
            System.out.println("Entity updated successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void add(Client client) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            ClientType clientType = session.createQuery("FROM ClientType ct WHERE ct.name = :name", ClientType.class)
                    .setParameter("name", client.getClientType().getClass().getSimpleName())
                    .uniqueResult();

            if(clientType == null) {
                session.save(client.getClientType());
            }
            else {
                client.setClientType(clientType);
            }
            session.save(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void remove(Client client) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        clients.remove(client);
    }

    public long size() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long count = (Long) session.createQuery("SELECT COUNT(c) FROM Client c").uniqueResult();
            transaction.commit();
            return count;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return 0;
    }

    public List<Client> getClients() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            clients = session.createQuery("FROM Client", Client.class).getResultList();

            transaction.commit();
            return clients;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return List.of();
    }

    public Client getClientByID(long ID) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Client client = session.createQuery("FROM Client c WHERE c.clientID = :clientID", Client.class)
                    .setParameter("clientID", ID)
                    .uniqueResult();

            transaction.commit();
            return client;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return null;
    }


}
