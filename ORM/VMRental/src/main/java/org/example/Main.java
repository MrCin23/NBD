package org.example;

import com.datastax.oss.driver.api.core.PagingIterable;
import org.example.consts.RentConsts;
import org.example.dao.ClientDao;
import org.example.dao.RentDao;
import org.example.dao.VMachineDao;
import org.example.mapper.*;
import org.example.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        DBConnection db = DBConnection.getInstance();
        db.initSession();
//        db.createKeyspace(); //todo to za pierwszym razem na nowej maszynie odkomentować
        db.createClientTable();
        db.createVMachineTable();
        db.createRentTables();
        x86 vm = new x86("AMD", 16, "32GB");
        Client client = new Client("firstname", "lastname", "email", new Standard());
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        ClientDao clientDao = clientMapper.clientDao();
        clientDao.create(client);
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
        vMachineDao.create(vm);
        RentMapper mapper = new RentMapperBuilder(db.getSession()).build();
        RentDao dao = mapper.rentDao();
        Rent rent = new Rent(client, vm, LocalDateTime.now());
        dao.create(rent);
        dao.endRent(rent);
//        List<Rent> rents = dao.findAllByTable(RentConsts.TABLE_CLIENTS);
//        System.out.println("all from clients");
//        for (Rent rent : rents) {
//            System.out.println(rent.toString());
//        }
//        System.out.println("all by client id");
//        List<Rent> rents1 = dao.findByClientId(UUID.fromString("d7948784-8dfc-4346-ad9c-02facdd8c6bf"));
//        for (Rent rent : rents1) {
//            System.out.println(rent.toString());
//        }
//        System.out.println("all by machine");
//        List<Rent> rents2 = dao.findByVMachineId(UUID.fromString("c167d507-409d-4d6e-868f-e17852c9dd55"));
//        for (Rent rent : rents2) {
//            System.out.println(rent.toString());
//        }



//
////        AppleArch vm = new AppleArch(16, "32GB");
//        vMachineDao.create(vm);
//        List<VMachine> vms = vMachineDao.getAll();
//        for (VMachine vm1 : vms) {
//            System.out.println(vm1.toString());
//        }
//        x86 toDelete = new x86(UUID.fromString("b79e3bc0-e6bb-42a5-9d7b-41727588628f"),"AMD", 16, "32GB");
//        vMachineDao.delete(toDelete);
//        x86 vmupdate = new x86(UUID.fromString("44dd6934-d712-46ba-9a71-947dc21731a1"),"INTEL", 16, "64GB");
//        vMachineDao.update(vmupdate); // dziala poggies!
//
//        Client client = new Client(UUID.fromString("b323a642-cd09-44a7-9825-0dbe76fa8810"),"FIRSTNAME", "lastname", "email", new Standard());
//        clientDao.update(client);
//        clientDao.delete(client);
//        db.dropClientTable();
//        db.dropVMachineTable();
//        db.dropRentTables();
        db.closeSession();
    }
}
