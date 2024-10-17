package org.example;

import jakarta.persistence.Entity;

@Entity
public class AppleArch extends VMachine{
    public AppleArch(int CPUNumber, String ramSize) {
        super(CPUNumber, ramSize, false);
        this.actualRentalPrice = getActualRentalPrice();
    }

    public AppleArch() {

    }

    @Override
    public float getActualRentalPrice() {
        float basePrice = 20;

        int ramInGB = Integer.parseInt(getRamSize().replaceAll("[^0-9]", ""));

        if (ramInGB > 4) {
            int timesDoubled = (int) (Math.log(ramInGB / 4) / Math.log(2));
            for (int i = 0; i < timesDoubled; i++) {
                basePrice += basePrice / 2;
            }
        }
        float threadMultiplier = getCPUNumber() / 2.0f;

        return 10 * basePrice * threadMultiplier;
    }
}
