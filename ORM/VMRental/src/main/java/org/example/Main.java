package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static VMachineManager vMachineManager = VMachineManager.getInstance();
    private static ClientManager clientManager = ClientManager.getInstance();
    private static RentManager rentManager = RentManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        long ID = 1;
//
//        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory()) {
//            try (Session session = sessionFactory.openSession()){
//                Transaction transaction = session.beginTransaction();
//
//                VMachine vMachine = session.createQuery("FROM VMachine vm WHERE vm.vMachineID = :vMachineID", VMachine.class)
//                        .setParameter("vMachineID", ID)
//                        .uniqueResult();
//
//                vMachine.setCPUNumber(4);
//
//                session.save(vMachine);
//                transaction.commit();
//            }
//        }

        while (true) {
            System.out.println("\nWelcome to the System! Choose an option:");
            System.out.println("1. Manage Virtual Machines");
            System.out.println("2. Manage Clients");
            System.out.println("3. Manage Rents");
            System.out.println("4. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manageVMachines();
                    break;
                case 2:
                    manageClients();
                    break;
                case 3:
                    manageRents();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void manageVMachines() {
        System.out.println("\nVirtual Machines:");
        System.out.println("1. Register new x86 Machine");
        System.out.println("2. Register new Apple Architecture Machine");
        System.out.println("3. Unregister a Machine");
        System.out.println("4. Update a Machine");
        System.out.println("5. View all Machines");
        System.out.println("6. Back");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                registerX86Machine();
                break;
            case 2:
                registerAppleMachine();
                break;
            case 3:
                unregisterMachine();
                break;
            case 4:
                updateMachine();
                break;
            case 5:
                System.out.println(vMachineManager.getAllVMachinesReport());
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void registerX86Machine() {
        System.out.println("Enter CPU Number:");
        int cpuNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter RAM size (e.g., '4GB'):");
        String ramSize = scanner.nextLine();
        System.out.println("Enter CPU Manufacturer (Intel/AMD/Other):");
        String cpuManufacturer = scanner.nextLine();

        vMachineManager.registerX86(cpuNumber, ramSize, cpuManufacturer);
        System.out.println("x86 Machine registered successfully!");
    }

    private static void registerAppleMachine() {
        System.out.println("Enter CPU Number:");
        int cpuNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter RAM size (e.g., '4GB'):");
        String ramSize = scanner.nextLine();

        vMachineManager.registerAppleArch(cpuNumber, ramSize);
        System.out.println("Apple Architecture Machine registered successfully!");
    }

    private static void unregisterMachine() {
        System.out.println("Enter machine ID to unregister:");
        long id = Long.parseLong(scanner.nextLine());

        VMachine vm = vMachineManager.getVMachine(id);
        if (vm != null) {
            vMachineManager.unregisterVMachine(id);
            System.out.println("Machine unregistered successfully!");
        } else {
            System.out.println("Machine not found.");
        }
    }

    private static void updateMachine() {
        System.out.println("Enter machine ID to update:");
        long id = Long.parseLong(scanner.nextLine());

        Map<String, Object> fieldsToUpdate = new HashMap<>();
        System.out.println("Enter field to update (e.g., 'cpuNumber' or 'ramSize'):");
        String field = scanner.nextLine();
        System.out.println("Enter new value:");
        Object value = scanner.nextLine();
        fieldsToUpdate.put(field, value);

        vMachineManager.updateField(id, fieldsToUpdate);
        System.out.println("Machine updated successfully!");
    }

    private static void manageClients() {
        System.out.println("\nClients:");
        System.out.println("1. Register new Client");
        System.out.println("2. Unregister a Client");
        System.out.println("3. Update a Client");
        System.out.println("4. View all Clients");
        System.out.println("5. Back");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                registerClient();
                break;
            case 2:
                unregisterClient();
                break;
            case 3:
                updateClient();
                break;
            case 4:
                System.out.println(clientManager.getAllClientsReport());
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void registerClient() {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter surname:");
        String surname = scanner.nextLine();
        System.out.println("Enter email address:");
        String email = scanner.nextLine();
        System.out.println("Enter client type (Admin/Standard):");
        String clientTypeInput = scanner.nextLine();
        ClientType clientType = clientTypeInput.equalsIgnoreCase("Admin") ? new Admin() : new Standard();

        clientManager.registerClient(firstName, surname, email, clientType);
        System.out.println("Client registered successfully!");
    }

    private static void unregisterClient() {
        System.out.println("Enter client ID to unregister:");
        long id = Long.parseLong(scanner.nextLine());

        Client client = clientManager.getClient(id);
        if (client != null) {
            clientManager.unregisterClient(id);
            System.out.println("Client unregistered successfully!");
        } else {
            System.out.println("Client not found.");
        }
    }

    private static void updateClient() {
        System.out.println("Enter client ID to update:");
        long id = Long.parseLong(scanner.nextLine());

        Map<String, Object> fieldsToUpdate = new HashMap<>();
        System.out.println("Enter field to update (e.g., 'firstName' or 'email'):");
        String field = scanner.nextLine();
        System.out.println("Enter new value:");
        Object value = scanner.nextLine();
        fieldsToUpdate.put(field, value);

        clientManager.updateField(id, fieldsToUpdate);
        System.out.println("Client updated successfully!");
    }

    private static void manageRents() {
        System.out.println("\nRents:");
        System.out.println("1. Register new Rent");
        System.out.println("2. End a Rent");
        System.out.println("3. View all Active Rents");
        System.out.println("4. Back");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                registerRent();
                break;
            case 2:
                endRent();
                break;
            case 3:
                System.out.println(rentManager.getAllRentsReport());
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void registerRent() {
        System.out.println("Enter client ID:");
        long clientId = Long.parseLong(scanner.nextLine());
        Client client = clientManager.getClient(clientId);

        System.out.println("Enter machine ID:");
        long machineId = Long.parseLong(scanner.nextLine());
        VMachine vMachine = vMachineManager.getVMachine(machineId);

        System.out.println("Enter start time (HH:mm):");
        String time = scanner.nextLine();

        if (client != null && vMachine != null) {
            rentManager.registerRent(client, vMachine, LocalDateTime.now());
            System.out.println("Rent registered successfully!");
        } else {
            System.out.println("Client or Machine not found.");
        }
    }

    private static void endRent() {
        System.out.println("Enter rent ID to end:");
        long rentId = Long.parseLong(scanner.nextLine());

        Rent rent = rentManager.getRent(rentId);
        if (rent != null) {
            System.out.println("Enter end time (HH:mm):");
            String time = scanner.nextLine();
            rentManager.endRent(rentId, LocalDateTime.now());
            System.out.println("Rent ended successfully!");
        } else {
            System.out.println("Rent not found.");
        }
    }
}
