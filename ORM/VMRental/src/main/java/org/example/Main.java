package org.example;

import com.datastax.oss.driver.api.core.PagingIterable;
import org.example.dao.ClientDao;
import org.example.dao.RentDao;
import org.example.dao.VMachineDao;
import org.example.mapper.*;
import org.example.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        DBConnection db = DBConnection.getInstance();
        db.initSession();
//        db.createClientTable();
//        db.createVMachineTable();
        db.createRentTables();
        x86 vm = new x86("AMD", 16, "32GB");
        Client client = new Client("firstname", "lastname", "email", new Standard());

        RentMapper mapper = new RentMapperBuilder(db.getSession()).build();
        RentDao dao = mapper.rentDao();
        dao.create(new Rent(client, vm, LocalDateTime.now()));

//
//        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
//        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
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
//        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
//        ClientDao clientDao = clientMapper.clientDao();
//
//        clientDao.create(client);
//        Client client = new Client(UUID.fromString("b323a642-cd09-44a7-9825-0dbe76fa8810"),"FIRSTNAME", "lastname", "email", new Standard());
//        clientDao.update(client);
//        clientDao.delete(client);
//        db.dropClientTable();
//        db.dropVMachineTable();
//        db.dropRentTables();
        db.closeSession();
    }
}
