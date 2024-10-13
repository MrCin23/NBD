package org.example;

import jakarta.persistence.Entity;

import java.util.UUID;
@Entity
public class AppleArch extends VMachine{
    public AppleArch(UUID vMachineID, int CPUNumber, String ramSize, boolean isRented) {
        super(vMachineID, CPUNumber, ramSize, isRented);
    }

    @Override
    public float getActualRentalPrice() {
        //TODO
        return 0;
    }
}
