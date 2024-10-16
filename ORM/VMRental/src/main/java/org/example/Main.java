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
        ClientManager cm = new ClientManager();
        //Client client = new Client("Michał", "Karbowańczyk", "m.karbowanczyk@p.lodz.pl", new Admin(69L));
        //cm.unregisterClient(cm.getClient(1));
        cm.registerClient("Mateusz", "Smoliński", "m.smolinski@p.lodz.pl", new Admin());
        cm.registerClient("Bartosz", "Lis", "bartosz.lis@p.lodz.pl", new Admin());



//        System.out.println(cm.getClientsAmount());
//
//        System.out.println(cm.getClient(4L));
//        System.out.println(cm.getClient(2137L));
//        var sessionFactory = new Configuration()
//                .addAnnotatedClass(Car.class)
//                // use H2 in-memory database
//                .setProperty(URL, "jdbc:postgresql://localhost:5432/vmrental")
//                .setProperty(USER, "postgres")
//                .setProperty(PASS, "password")
//                // use Agroal connection pool
//                .setProperty("hibernate.agroal.maxSize", 20)
//                // display SQL in console
//                .setProperty(SHOW_SQL, true)
//                .setProperty(FORMAT_SQL, true)
//                .setProperty(HIGHLIGHT_SQL, true)
//                .buildSessionFactory();
//
//        // export the inferred database schema
//        sessionFactory.getSchemaManager().exportMappedObjects(true);
//
//        // persist an entity
//        sessionFactory.inTransaction(session -> {
//            session.persist(new Car(1234L,"Hibernate in Action"));
//        });
//
//        // query data using HQL
//        sessionFactory.inSession(session -> {
//            out.println(session.createSelectionQuery("select carId||': '||name from Car").getSingleResult());
//        });
//
//        // query data using criteria API
//        sessionFactory.inSession(session -> {
//            var builder = sessionFactory.getCriteriaBuilder();
//            var query = builder.createQuery(String.class);
//            var car = query.from(Car.class);
//           query.select(builder.concat(builder.concat(car.get("carId"), builder.literal(": ")),
//                    car.get("name")));
//
////           query.select(builder.concat(builder.concat(car.get(Car_.carId), builder.literal(": ")),
////                    car.get(Car_.name)));
//            out.println(session.createSelectionQuery(query).getSingleResult());
//        });
    }
}
