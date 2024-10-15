package org.example;

import java.sql.Time;

//RentManager jako Singleton
public final class RentManager {
    private static RentManager instance;
    private Repository activeRentsRepository;
    private Repository archiveRentsRepository;

    public RentManager() {
        activeRentsRepository = new Repository();
        archiveRentsRepository = new Repository();
    }

    public static RentManager getInstance() {
        if (instance == null) {
            instance = new RentManager();
        }
        return instance;
    }

    public void registerExsistingRent(Rent rent) {
        activeRentsRepository.add(rent);
    }

    public void registerRent(long rentID, Client client, VMachine vMachine, Time beginTime) {
        Rent rent = new Rent(rentID, client, vMachine, beginTime);
        registerExsistingRent(rent);
    }

    public void endRent(Rent rent, Time endTime) {
        rent.endRent(endTime);
        activeRentsRepository.remove(rent);
        archiveRentsRepository.add(rent);
    }

    //METHODS-----------------------------------
//TODO
    public String getAllRentsReport() {
        return this.activeRentsRepository.getElements().toString();
    }
    public Rent getRent(long rentID) {
        return (Rent) activeRentsRepository.getElementByID(rentID);
    }
}

