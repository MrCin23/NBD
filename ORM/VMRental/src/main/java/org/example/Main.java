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
    private static final ClientManager clientManager = ClientManager.getInstance();
    private static RentManager rentManager = RentManager.getInstance();
//    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        clientManager.registerClient("Bartosz", "Lis", "bartosz.lis@p.lodz.pl", new Admin());
////        System.out.println(clientManager.getAllClientsReport());
//        vMachineManager.registerAppleArch(8, "16GB");
//////        vMachineManager.registerX86(16, "32GB", "AMD");
        Client client = new Client("Mateusz", "Smoliński", "mateusz.smolinski@p.lodz.pl", new Standard());
        clientManager.registerExistingClient(client);
        System.out.println(clientManager.getAllClientsReport());
        VMachine vMachine = new x86(16, "64GB", "Intel");
        vMachineManager.registerExistingVMachine(vMachine);
        Rent rent = new Rent(client, vMachine, LocalDateTime.now());
        rentManager.registerExistingRent(rent);
        vMachine = new x86(16, "32GB", "AMD");
        vMachineManager.registerExistingVMachine(vMachine);
        rent = new Rent(client, vMachine, LocalDateTime.now());
        rentManager.registerExistingRent(rent);
        vMachine = new AppleArch(8, "16GB");
        vMachineManager.registerExistingVMachine(vMachine);
        rent = new Rent(client, vMachine, LocalDateTime.now());
        rentManager.registerExistingRent(rent);
        vMachine = new x86(16, "32GB", "AMD");
        vMachineManager.registerExistingVMachine(vMachine);
        rent = new Rent(client, vMachine, null);
        rentManager.registerExistingRent(rent);
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
