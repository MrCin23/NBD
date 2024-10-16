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
        // Bazowa cena dla 4 GB RAM wynosi 10
        float basePrice = 20;

        // Konwersja ramSize na liczbę w GB
        int ramInGB = Integer.parseInt(getRamSize().replaceAll("[^0-9]", ""));

        // Obliczanie ceny w zależności od ilości RAM
        if (ramInGB > 4) {
            int timesDoubled = (int) (Math.log(ramInGB / 4) / Math.log(2));
            for (int i = 0; i < timesDoubled; i++) {
                basePrice += basePrice / 2;
            }
        }

        // Mnożnik na podstawie liczby wątków (CPUNumber) podzielonej przez 2
        float threadMultiplier = getCPUNumber() / 2.0f;



        // Finalna cena = baza * mnożnik za wątki * mnożnik producenta
        return 10 * basePrice * threadMultiplier;
    }
}
