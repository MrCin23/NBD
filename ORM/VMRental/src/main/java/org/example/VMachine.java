package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;
@Entity
public abstract class VMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID vMachineID;
    private int CPUNumber;
    private String ramSize;
    boolean isRented;

    public VMachine(UUID vMachineID, int CPUNumber, String ramSize, boolean isRented) {
        this.vMachineID = vMachineID;
        this.CPUNumber = CPUNumber;
        this.ramSize = ramSize;
        this.isRented = isRented;
    }

    public UUID getvMachineID() {
        return vMachineID;
    }

    public void setvMachineID(UUID vMachineID) {
        this.vMachineID = vMachineID;
    }

    public int getCPUNumber() {
        return CPUNumber;
    }

    public void setCPUNumber(int CPUNumber) {
        this.CPUNumber = CPUNumber;
    }

    public String getRamSize() {
        return ramSize;
    }

    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public float getActualRentalPrice() {
        //TODO
        return 0;
    }
};


