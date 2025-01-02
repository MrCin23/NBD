package org.example.manager;

import org.example.DBConnection;
import org.example.dao.RentDao;
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
    }

    public void registerRent(Client client, VMachine vMachine, LocalDateTime beginTime) {
        Rent rent = new Rent(client, vMachine, beginTime);
        registerExistingRent(rent);
    }

    public void registerExistingRent(Rent rent) {
        rentDao.create(rent);
    }

    public List<Rent> getRentsByClientID(UUID uuid) {
        return rentDao.findByClientId(uuid);
    }

    public List<Rent> getRentsByVMachineID(UUID uuid) {
        return rentDao.findByVMachineId(uuid);
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
