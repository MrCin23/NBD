package org.example;



public class Main {
    public static void main(String[] args) {

        ClientManager cm = new ClientManager();
//        Client client = new Client(3L, "Michał", "Karbowańczyk", "m.karbowanczyk@p.lodz.pl", new Admin(69L));
//        cm.registerExsistingClient(client);
//
//        cm.registerExsistingClient(client);

        System.out.println(cm.getClientsAmount());

        System.out.println(cm.getClient(4L));
        System.out.println(cm.getClient(2137L));
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
