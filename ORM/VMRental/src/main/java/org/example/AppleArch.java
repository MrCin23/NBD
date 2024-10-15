package org.example;

import jakarta.persistence.Entity;

@Entity
public class AppleArch extends VMachine{
    public AppleArch(long vMachineID, int CPUNumber, String ramSize) {
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
