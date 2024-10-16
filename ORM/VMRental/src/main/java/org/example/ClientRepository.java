package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

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


    public void add(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

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
            throw new RuntimeException(e);
        }
    }

    public void remove(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clients.remove(client);
    }

    public long size() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Long count = (Long) session.createQuery("SELECT COUNT(c) FROM Client c").uniqueResult();
            transaction.commit();
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Client> getClients() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            clients = session.createQuery("FROM Client", Client.class).getResultList();

            transaction.commit();
            return clients;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Client getClientByID(long ID) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Client client = session.createQuery("FROM Client c WHERE c.clientID = :clientID", Client.class)
                    .setParameter("clientID", ID)
                    .uniqueResult();

            transaction.commit();
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
