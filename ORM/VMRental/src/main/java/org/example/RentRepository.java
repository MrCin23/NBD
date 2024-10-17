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

    public long size(boolean active) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Long count;
            if (active) {
                count = (Long) session.createQuery("SELECT COUNT(rent.rentID) FROM Rent rent WHERE rent.endTime is null")
                                .uniqueResult();
            }
            else {
                count = (Long) session.createQuery("SELECT COUNT(rent.rentID) FROM Rent rent WHERE rent.endTime is not null")
                        .uniqueResult();
            }
            transaction.commit();
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long size() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Long count;
            count = (Long) session.createQuery("SELECT COUNT(Rent.rentID) FROM Rent").uniqueResult();
            transaction.commit();
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Rent> getRents(boolean active) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Rent> rents;
            if (active) {
                rents = session.createQuery("FROM Rent rent WHERE rent.endTime is null", Rent.class).getResultList();
            }
            else {
                rents = session.createQuery("FROM Rent rent WHERE rent.endTime is not null", Rent.class).getResultList();
            }
            transaction.commit();
            return rents;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Rent> getRents() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Rent> rents;
            rents = session.createQuery("FROM Rent", Rent.class).getResultList();
            transaction.commit();
            return rents;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RepoElement getRentByID(long ID) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Rent rent = session.createQuery("FROM Rent rent WHERE rent.rentID = :rent", Rent.class)
                    .setParameter("rent", ID)
                    .uniqueResult();

            transaction.commit();
            return rent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
