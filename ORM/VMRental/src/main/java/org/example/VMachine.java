package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public abstract class VMachine implements RepoElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long vMachineID;
    @NotNull
    private int CPUNumber;
    @NotNull
    private String ramSize;
    @NotNull
    private boolean isRented;
    @NotNull
    protected float actualRentalPrice;

    public VMachine(int CPUNumber, String ramSize, boolean isRented) {
        this.CPUNumber = CPUNumber;
        this.ramSize = ramSize;
        this.isRented = isRented;
    }

    public VMachine() {

    }

    public long getvMachineID() {
        return vMachineID;
    }

    //REPO TEMPLATE
    public long getID() {
        return vMachineID;
    }

    public void setvMachineID(long vMachineID) {
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


