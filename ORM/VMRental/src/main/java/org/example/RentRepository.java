package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentRepository {
    List<Rent> rents;
    SessionFactory sessionFactory;

    public RentRepository() {
        rents = new ArrayList<Rent>();
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void add(Rent rent) {
        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            ClientType clientType = session.createQuery("FROM ClientType ct WHERE ct.name = :name", ClientType.class)
                    .setParameter("name", rent.getClient().getClientType().getName())
                    .uniqueResult();

            if(clientType == null) {
                session.save(rent.getClient().getClientType());
            }
            else {
                rent.getClient().setClientType(clientType);
            }

            Client c = session.createQuery("FROM Client c WHERE c.emailAddress = :mail", Client.class)
                    .setParameter("mail", rent.getClient().getEmailAddress())
                    .uniqueResult();

            if(c == null) {
                session.save(rent.getClient());
            }
            else {
                rent.setClient(c);
            }
            session.save(rent);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public int size() {
        return rents.size();
    }

    public List<Rent> getElements() {
        return rents;
    }

    public RepoElement getElementByID(long ID) {
        for (RepoElement rents : rents) {
            if (rents.getID() == ID) { //TODO to pewnie nie działa, wypada to jakoś naprawić (może interface)
                return rents;
            }
        }
        return null;
    }


//-----------------------------------------------------------

}
