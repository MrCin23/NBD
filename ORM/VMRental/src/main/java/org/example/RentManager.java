package org.example;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Map;

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
    public Rent getRent(long rentID) {
        return (Rent) rentRepository.getRentByID(rentID);
    }
}

