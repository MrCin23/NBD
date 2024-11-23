package org.example.manager;

import org.example.model.Client;
import org.example.model.MongoUUID;
import org.example.model.Rent;
import org.example.model.VMachine;
import org.example.repository.RentRepository;

import java.time.LocalDateTime;

//RentManager jako Singleton
public final class RentManager {
    private static RentManager instance;
    private final RentRepository rentRepository;

    public RentManager() {
        rentRepository = new RentRepository();
    }

    public static RentManager getInstance() {
        if (instance == null) {
            instance = new RentManager();
        }
        return instance;
    }

    public void registerExistingRent(Rent rent) {
        rentRepository.add(rent);
    }

    public void registerRent(Client client, VMachine vMachine, LocalDateTime beginTime) {
        Rent rent = new Rent(client, vMachine, beginTime);
        registerExistingRent(rent);
    }

    public void endRent(MongoUUID id, LocalDateTime endTime) {
        rentRepository.endRent(id, endTime);
    }

    //METHODS-----------------------------------
    public String getAllRentsReport() {
        return this.rentRepository.getRents().toString();
    }
    public Rent getRent(MongoUUID uuid) {
        return rentRepository.getRentByID(uuid);
    }
    public int size(){
        return rentRepository.getRents().size();
    }
}

