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
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            VMachine vMachine = session.createQuery("FROM VMachine vm WHERE vm.vMachineID = :vMachineID", VMachine.class)
                    .setParameter("vMachineID", id)
                    .uniqueResult();

            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValuee = entry.getValue();
                try {
                    Field field = VMachine.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    String fieldValue = (String)fieldValuee;
                    if (type == String.class) {
                        field.set(vMachine, fieldValue);
                    }
                    else if (type == int.class || type == Integer.class) {
                        field.set(vMachine, Integer.parseInt(fieldValue));
                    }
                    else if (type == boolean.class || type == Boolean.class) {
                        field.set(vMachine, Boolean.parseBoolean(fieldValue));
                    }
                    else if (type == long.class || type == Long.class) {
                        field.set(vMachine, Long.parseLong(fieldValue));
                    }
                    else if (type == double.class || type == Double.class) {
                        field.set(vMachine, Double.parseDouble(fieldValue));
                    }
                    else if (type == float.class || type == Float.class) {
                        field.set(vMachine, Float.parseFloat(fieldValue));
                    }
                    else {
                        field.set(vMachine, type.cast(fieldValue));
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException("Error updating field: " + fieldName, e);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void add(VMachine vMachine) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(vMachine);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void remove(VMachine vMachine) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(vMachine);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        vMachines.remove(vMachine);
    }

    public long size() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long count = (Long) session.createQuery("SELECT COUNT(vm) FROM VMachine vm").uniqueResult();
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

    public List<VMachine> getVMachines() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            vMachines = session.createQuery("FROM VMachine ", VMachine.class).getResultList();

            transaction.commit();
            return vMachines;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return List.of();
    }

    public RepoElement getVMachineByID(long ID) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            VMachine vMachine = session.createQuery("FROM VMachine vm WHERE vm.vMachineID = :vMachineID", VMachine.class)
                    .setParameter("vMachineID", ID)
                    .uniqueResult();

            transaction.commit();
            return vMachine;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
}
