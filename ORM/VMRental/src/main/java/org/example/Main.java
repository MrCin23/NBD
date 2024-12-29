package org.example;

import org.example.dao.ClientDao;
import org.example.dao.VMachineDao;
import org.example.mapper.ClientMapper;
import org.example.mapper.ClientMapperBuilder;
import org.example.mapper.VMachineMapper;
import org.example.mapper.VMachineMapperBuilder;
import org.example.model.AppleArch;
import org.example.model.Client;
import org.example.model.Standard;
import org.example.model.x86;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        DBConnection db = DBConnection.getInstance();
        db.initSession();
//        db.createClientTable();
        db.createVMachineTable();

        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
        x86 vm = new x86("AMD", 16, "32GB"); //to nie dziala xd
//        AppleArch vm = new AppleArch(16, "32GB"); //to dziala
        vMachineDao.create(vm);
//
//        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
//        ClientDao clientDao = clientMapper.clientDao();
//
//        Client client = new Client("firstname", "lastname", "email", new Standard());
//        clientDao.create(client);
//        Client client = new Client(UUID.fromString("b323a642-cd09-44a7-9825-0dbe76fa8810"),"FIRSTNAME", "lastname", "email", new Standard());
//        clientDao.update(client);
//        clientDao.delete(client);
//        db.dropClientTable();
//        db.dropVMachineTable();
        db.closeSession();
    }
}
