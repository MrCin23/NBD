package org.example.manager;

import org.example.DBConnection;
import org.example.dao.ClientDao;
import org.example.dao.RentDao;
import org.example.dao.VMachineDao;
import org.example.mapper.*;
import org.example.model.Client;
import org.example.model.Rent;
import org.example.model.VMachine;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RentManager {
    private static RentManager instance;
    DBConnection db;
    RentDao rentDao;
    ClientDao clientDao;
    VMachineDao vmDao;

    public static synchronized RentManager getInstance() {
        if (instance == null) {
            instance = new RentManager();
        }
        return instance;
    }

    private RentManager() {
        db = DBConnection.getInstance();
        db.initSession();
        db.createRentTables();
        RentMapper rentMapper = new RentMapperBuilder(db.getSession()).build();
        rentDao = rentMapper.rentDao();
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        clientDao = clientMapper.clientDao();
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        vmDao = vMachineMapper.vMachineDao();
    }

    public void registerRent(Client client, VMachine vMachine, LocalDateTime beginTime) {
        Rent rent = new Rent(client, vMachine, beginTime);
        registerExistingRent(rent);
    }

    public void registerExistingRent(Rent rent) {
        rentDao.create(rent);
    }

    public List<Rent> getRentsByClientID(UUID uuid) {
        //find client
        Client client = clientDao.findById(uuid); //Q1
        //find rent
        List<Rent> rents = rentDao.findByClientId(uuid); //client = null, vmachine = null Q3
        //client -> rent, vmachine -> rent
        for (Rent rent : rents) {
            rent.setClient(client); //client no longer null
            //find vmachine from rent id
            VMachine vm = vmDao.findById(rent.getVmID()); //Q5
            rent.setVMachine(vm); //vmachine no longer null
        }
        return rents;
    }

    public List<Rent> getRentsByVMachineID(UUID uuid) {
        //find vm
        VMachine vm = vmDao.findById(uuid); //Q2
        //find rent
        List<Rent> rents = rentDao.findByVMachineId(uuid); //client = null, vmachine = null Q4
        //client -> rent, vmachine -> rent
        for (Rent rent : rents) {
            rent.setVMachine(vm); //vmachine no longer null
            //find client from rent id
            Client client = clientDao.findById(rent.getClientID()); //Q6
            rent.setClient(client); //client no longer null
        }
        return rents;
    }

    public void endRent(UUID clientID, UUID vMachineID, LocalDateTime endTime) {
        //TODO nienajszczęśliwsze rozwiązanie, ale może o to chodzi właśnie
        List<Rent> rents = rentDao.findByClientId(clientID);
        for(Rent rent : rents){
            if(rent.getVmID().equals(vMachineID) && rent.getEndTime() == null) {
               rentDao.endRent(rent);
               break;
           }
        }
    }
}
