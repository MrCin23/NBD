package org.example;


public abstract class VMachine{

    private long vMachineID;
    private int CPUNumber;
    private String ramSize;
    private boolean isRented;
    protected float actualRentalPrice;
    int version;

    public VMachine(int CPUNumber, String ramSize, boolean isRented) {
        this.CPUNumber = CPUNumber;
        this.ramSize = ramSize;
        this.isRented = isRented;
    }

    public VMachine() {}

    public long getvMachineID() {
        return vMachineID;
    }

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
        return 0;
    }
};


