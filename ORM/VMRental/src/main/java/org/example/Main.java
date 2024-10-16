package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class Main {
    public static void main(String[] args) {

//        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            ClientType clientType = new Admin();
//            ClientType clientType2 = new Standard();
//            session.save(clientType);
//            session.save(clientType2);
//            transaction.commit();
//        }
        AppleArch apple = new AppleArch(16, "64GB");
        VMachineManager mgr = new VMachineManager();
        mgr.registerExsistingVMachine(apple);
        mgr.getVMachinesAmount();
        System.out.println(mgr.getAllVMachinesReport());
        //mgr.registerExsistingVMachine(normal);
//        ClientManager cm = new ClientManager();
//        Client client = new Client("Marcin", "Targoński", "247810@edu.p.lodz.pl", new Standard());
//        //cm.unregisterClient(cm.getClient(1));
//        cm.registerExsistingClient(client);
//        cm.registerClient("Piotr", "Sokoliński", "247790@edu.p.lodz.pl", new Standard());


    }
}
