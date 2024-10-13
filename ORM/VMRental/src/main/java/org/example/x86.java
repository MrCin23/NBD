package org.example;

import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
public class x86 extends VMachine {
    private String CPUManufacturer;

    public x86(UUID vMachineID, int CPUNumber, String ramSize, boolean isRented, String CPUManufacturer) {
        super(vMachineID, CPUNumber, ramSize, isRented);
        this.CPUManufacturer = CPUManufacturer;
    }

    @Override
    public float getActualRentalPrice() {
        //TODO
        return 0;
    }
}
