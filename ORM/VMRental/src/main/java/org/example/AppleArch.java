package org.example;

import jakarta.persistence.Entity;

import java.util.UUID;
@Entity
public class AppleArch extends VMachine{
    public AppleArch(UUID vMachineID, int CPUNumber, String ramSize) {
        super(vMachineID, CPUNumber, ramSize, false);
    }

    public AppleArch() {

    }

    @Override
    public float getActualRentalPrice() {
        //TODO
        return 0;
    }
}
