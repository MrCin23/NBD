package org.example;

import jakarta.persistence.Entity;


@Entity
public class x86 extends VMachine {
    private String CPUManufacturer;

    public x86(int CPUNumber, String ramSize, String CPUManufacturer) {
        super(CPUNumber, ramSize, false);
        this.CPUManufacturer = CPUManufacturer;
        this.actualRentalPrice = getActualRentalPrice();
    }

    public x86() {

    }

    @Override
    public float getActualRentalPrice() {
        // Bazowa cena dla 4 GB RAM wynosi 10
        float basePrice = 10;

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

        // Mnożnik na podstawie producenta CPU
        float manufacturerMultiplier = 1;
        if (CPUManufacturer.equalsIgnoreCase("Intel")) {
            manufacturerMultiplier = 3;
        } else if (CPUManufacturer.equalsIgnoreCase("AMD")) {
            manufacturerMultiplier = 2;
        }

        // Finalna cena = baza * mnożnik za wątki * mnożnik producenta
        return basePrice * threadMultiplier * manufacturerMultiplier;
    }
}
