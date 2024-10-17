package org.example;

import java.sql.Time;
import java.util.Map;

//RentManager jako Singleton
public final class RentManager {
    private static RentManager instance;
    private RentRepository activeRentsRepository;
    private RentRepository archiveRentsRepository;

    public RentManager() {
        activeRentsRepository = new RentRepository();
        archiveRentsRepository = new RentRepository();
    }

    public static RentManager getInstance() {
        if (instance == null) {
            instance = new RentManager();
        }
        return instance;
    }

    public void registerExistingRent(Rent rent) {
        activeRentsRepository.add(rent);
    }

    public void registerRent(Client client, VMachine vMachine, Time beginTime) {
        Rent rent = new Rent(client, vMachine, beginTime);
        registerExistingRent(rent);
    }

    public void endRent(Rent rent, Time endTime) {
        rent.endRent(endTime);
        //activeRentsRepository.remove(rent);
        archiveRentsRepository.add(rent);
    }

    public void updateField(long id, Map<String, Object> fieldsToUpdate, Boolean active) {
        if(active) {
            activeRentsRepository.update(id, fieldsToUpdate);
        }
        else {
            archiveRentsRepository.update(id, fieldsToUpdate);
        }
    }

    //METHODS-----------------------------------
//TODO
    public String getAllRentsReport() {
        return this.activeRentsRepository.getRents().toString();
    }
    public Rent getRent(long rentID) {
        return (Rent) activeRentsRepository.getRentByID(rentID);
    }
}

