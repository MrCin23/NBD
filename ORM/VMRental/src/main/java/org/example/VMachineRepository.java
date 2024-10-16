package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class VMachineRepository {
    List<VMachine> vMachines;
    private final String url = "jdbc:postgresql://localhost:5432/vmrental";
    private final String user = "postgres";
    private final String password = "password";
    SessionFactory sessionFactory;

    public VMachineRepository() {
        vMachines = new ArrayList<VMachine>();
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

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
