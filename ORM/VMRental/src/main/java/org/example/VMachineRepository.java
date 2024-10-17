package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VMachineRepository {
    List<VMachine> vMachines;
    SessionFactory sessionFactory;

    public VMachineRepository() {
        vMachines = new ArrayList<VMachine>();
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void update(long id, Map<String, Object> fieldsToUpdate) {
        // Check if there are fields to update
        if (fieldsToUpdate == null || fieldsToUpdate.isEmpty()) {
            throw new IllegalArgumentException("No fields to update.");
        }
        try (Session session = sessionFactory.openSession()) {
            // Start the transaction
            Transaction transaction = session.beginTransaction();

            // Retrieve the entity by its ID
            Object entity = session.get(VMachine.class, id);
            if (entity == null) {
                throw new IllegalArgumentException("Entity not found with id: " + id);
            }

            // Update the fields dynamically (use reflection)
            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                // Use reflection to set the field value on the entity
                try {
                    Field field = VMachine.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    if(field.getType() == String.class){
                        field.set(entity, fieldValue);
                    }
                    else {
                        field.set(entity, fieldValue);
                    }
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
            throw new RuntimeException(e);
        }
    }

    public void add(VMachine vMachine) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(vMachine);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(VMachine vMachine) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(vMachine);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        vMachines.remove(vMachine);
    }

    public long size() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Long count = (Long) session.createQuery("SELECT COUNT(vm) FROM VMachine vm").uniqueResult();
            transaction.commit();
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<VMachine> getVMachines() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            vMachines = session.createQuery("FROM VMachine ", VMachine.class).getResultList();

            transaction.commit();
            return vMachines;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RepoElement getVMachineByID(long ID) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            VMachine vMachine = session.createQuery("FROM VMachine vm WHERE vm.vMachineID = :vMachineID", VMachine.class)
                    .setParameter("vMachineID", ID)
                    .uniqueResult();

            transaction.commit();
            return vMachine;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
