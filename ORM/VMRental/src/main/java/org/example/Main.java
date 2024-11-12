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
//        System.out.println(clientManager.getAllClientsReport());
//        vMachineManager.registerAppleArch(8, "16GB");
//        vMachineManager.registerX86(16, "32GB", "AMD");
//        System.out.println(vMachineManager.getAllVMachinesReport());
        Client client = new Client("Mateusz", "Smoliński", "mateusz.smolinski@p.lodz.pl", new Standard());
        VMachine vMachine = new x86(16, "64GB", "Intel");
        rentManager.registerRent(client, vMachine, LocalDateTime.now());
        System.out.println(rentManager.getAllRentsReport());
    }
}
