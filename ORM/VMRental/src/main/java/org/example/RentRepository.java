package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RentRepository {
    List<Rent> rents;
    SessionFactory sessionFactory;

    public RentRepository() {
        rents = new ArrayList<Rent>();
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void endRent(long id, LocalDateTime endTime){
        if(endTime == null){
            endTime = LocalDateTime.now();
        }
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Rent rent = session.createQuery("FROM Rent rent WHERE rent.rentID = :rent", Rent.class)
                    .setParameter("rent", id)
                    .uniqueResult();
            rent.endRent(endTime);
            session.lock(rent.getClient(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            session.save(rent.getvMachine());
            session.save(rent);
            transaction.commit();
        } catch (OptimisticLockException e) {
            System.out.println("Równoczesna modyfikacja wykryta dla klienta.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

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
            Object entity = session.get(Rent.class, id);
            if (entity == null) {
                throw new IllegalArgumentException("Entity not found with id: " + id);
            }

            // Update the fields dynamically (use reflection)
            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                // Use reflection to set the field value on the entity
                try {
                    Field field = Rent.class.getDeclaredField(fieldName);
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

    public void add(Rent rent) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();


            long currentlyRented = (long) session.createQuery("SELECT COUNT(rent.rentID) FROM Rent rent WHERE rent.endTime is null AND rent.client.clientID = :rentID")
                    .setParameter("rentID", rent.getClient().getclientID())
                    .uniqueResult();
            boolean rented = (boolean) session.createQuery("SELECT vm.isRented FROM VMachine vm WHERE vm.vMachineID = :rentID")
                    .setParameter("rentID", rent.getvMachine().getvMachineID())
                    .uniqueResult();
            ClientType clientType = session.createQuery("FROM ClientType ct WHERE ct.name = :name", ClientType.class)
                    .setParameter("name", rent.getClient().getClientType().getName())
                    .uniqueResult();
            if (rented) {
                throw new RuntimeException("Virtual Machine is already rented.");
            }

            if (clientType == null) {
                session.save(rent.getClient().getClientType());
            } else {
                rent.getClient().setClientType(clientType);
            }

            if (currentlyRented >= clientType.getMaxRentedMachines()) {
                throw new RuntimeException("Client has reached their maximum active rent amount.");
            }
            Client c = session.createQuery("FROM Client c WHERE c.emailAddress = :mail", Client.class)
                    .setParameter("mail", rent.getClient().getEmailAddress())
                    .uniqueResult();

            if (c == null) {
                session.save(rent.getClient());
            } else {
                rent.setClient(c);
            }
            session.lock(rent.getClient(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

            rent.getvMachine().setRented(true);
            session.update(rent.getvMachine());
            session.save(rent);
            transaction.commit();
        } catch (OptimisticLockException e) {
                System.out.println("Równoczesna modyfikacja wykryta dla klienta.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public long size(boolean active) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
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
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return 0;
    }

    public long size() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long count;
            count = (Long) session.createQuery("SELECT COUNT(Rent.rentID) FROM Rent").uniqueResult();
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

    public List<Rent> getRents(boolean active) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
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
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return List.of();
    }

    public List<Rent> getRents() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<Rent> rents;
            rents = session.createQuery("FROM Rent", Rent.class).getResultList();
            transaction.commit();
            return rents;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return List.of();
    }

    public Rent getRentByID(long ID) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Rent rent = session.createQuery("FROM Rent rent WHERE rent.rentID = :rent", Rent.class)
                    .setParameter("rent", ID)
                    .uniqueResult();

            transaction.commit();
            return rent;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
}
