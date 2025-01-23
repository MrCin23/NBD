package org.example;

//import org.apache.avro.mapred.AvroMapper;

import org.example.repository.Consumer;

import java.io.IOException;

public class ConsumerMain {

//    private static VMachineManager vMachineManager = VMachineManager.getInstance();
//    private static final ClientManager clientManager = ClientManager.getInstance();
//    private static RentManager rentManager = RentManager.getInstance();
//    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        do {
            System.out.println("Oczekiwanie na powstanie tematu");
            Thread.sleep(1000);
        } while(!Consumer.doesTopicExist("rents"));
        System.out.println("Upa");
        Consumer.consumeTopicsByGroup("rents");



//        Schema schema = new Schema.Parser().parse(new File("src/test/avro/user.testavro"));
//        GenericRecord user = new GenericData.Record(schema);
//
//        user.put("entityId", "00000000-0000-0000-0000-000000000000");
//        user.put("firstName", "a");
//        user.put("surname", "a");
//        user.put("emailAddress", "a@a.a");
//        user.put("currentRents", 1);
//
//        Schema clientTypeSchema = schema.getField("clientType").schema().getTypes().get(0); // Assuming Standard is the first type
//        GenericRecord standard = new GenericData.Record(clientTypeSchema);
//        standard.put("entityId", "11111111-1111-1111-1111-111111111111");
//        standard.put("maxRentedMachines", 3);
//        standard.put("name", "asdasds");
//
//        user.put("clientType", standard);
//
//        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
//        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
//            dataFileWriter.create(schema, new File("generic.avro"));
//            dataFileWriter.append(user);
//        }
//        Client client = Client.newBuilder()
//                .setEntityId(UUID.randomUUID())
//                .setFirstName("a")
//                .setSurname("a")
//                .setClientType(new Standard())
//                .setEmailAddress("a")
//                .setCurrentRents(1)
//                .build();
//
//        System.out.println(((ClientType)client.getClientType()).getEntityId());


//        Schema schema = new Schema.Parser().parse(new File("src/test/avro/rent.testavro"));
//        GenericRecord rent = new GenericData.Record(schema);
//
//        rent.put("entityId", "00000000-0000-0000-0000-000000000000");
//        rent.put("CPUNumber", 1);
//        rent.put("ramSize", "16GiB");
//        rent.put("isRented", 0);
//        rent.put("actualRentalPrice", 2137.0f);
//
//        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
//        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
//            dataFileWriter.create(schema, new File("rent_generic.avro"));
//            dataFileWriter.append(rent);
//        }



//        clientManager.registerClient("Bartosz", "Lis", "bartosz.lis@p.lodz.pl", new Admin());
////        System.out.println(clientManager.getAllClientsReport());
//        vMachineManager.registerAppleArch(8, "16GB");
//////        vMachineManager.registerX86(16, "32GB", "AMD");
//        Client client = new Client("Mateusz", "Smoliński", "mateusz.smolinski@p.lodz.pl", new Standard());
//        clientManager.registerExistingClient(client);
//        System.out.println(clientManager.getAllClientsReport());
//        VMachine vMachine = new x86(16, "64GB", "Intel");
//        vMachineManager.registerExistingVMachine(vMachine);
//        Rent rent = new Rent(client, vMachine, LocalDateTime.now());
//        rentManager.registerExistingRent(rent);
//        vMachine = new x86(16, "32GB", "AMD");
//        vMachineManager.registerExistingVMachine(vMachine);
//        rent = new Rent(client, vMachine, LocalDateTime.now());
//        rentManager.registerExistingRent(rent);
//        vMachine = new AppleArch(8, "16GB");
//        vMachineManager.registerExistingVMachine(vMachine);
//        rent = new Rent(client, vMachine, LocalDateTime.now());
//        rentManager.registerExistingRent(rent);
//        vMachine = new x86(16, "32GB", "AMD");
//        vMachineManager.registerExistingVMachine(vMachine);
//        rent = new Rent(client, vMachine, null);
//        rentManager.registerExistingRent(rent);
//        System.out.println(rentManager.getAllRentsReport());
//        rentManager.endRent(rent.getEntityId(),LocalDateTime.of(2024,11,16,14,45));
//        System.out.println(rentManager.getAllRentsReport());
////        Map<String, Object> update = new HashMap<>();
////        update.put("CPUNumber", 2137);
////        update.put("ramSize", "128GB");
////        //update.put("isRented", 1);
////        //System.out.println(vMachineManager.getAllVMachinesReport());
////        //vMachineManager.update(vMachine.getEntityId(), update);
////        //System.out.println(vMachineManager.getAllVMachinesReport());
////        System.out.println(rentManager.getAllRentsReport());
////        //System.out.println(vMachineManager.getAllVMachinesReport());
////
////
////        rentManager.registerRent(client, vMachine, LocalDateTime.now());
////        //rent.endRent(LocalDateTime.of(2024,11,15,14,45));
////        //rentManager.registerExistingRent(rent);
//        //System.out.println(rentManager.getAllRentsReport());

    }
}
