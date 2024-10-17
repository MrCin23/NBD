package org.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DialectOverride;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Architecture",discriminatorType=DiscriminatorType.STRING)
public abstract class VMachine {
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
    @Version
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


