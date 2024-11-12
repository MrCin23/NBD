package org.example;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class AppleArch extends VMachine{
    public AppleArch(int CPUNumber, String ramSize) {
        super(CPUNumber, ramSize, false);
        this.actualRentalPrice = getActualRentalPrice();
    }

    public AppleArch() {
        super();
    }

    @BsonCreator
    public AppleArch(@BsonProperty("_id") MongoUUID uuid, @BsonProperty("CPUNumber") int CPUNumber, @BsonProperty("ramSize") String ramSize,
                     @BsonProperty("isRented") boolean isRented, @BsonProperty("actualRentalPrice") float actualRentalPrice) {
        super(uuid, CPUNumber, ramSize, isRented, actualRentalPrice);
        this.actualRentalPrice = actualRentalPrice;
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

    public String toString() {
        return "AppleArch: " + this.getEntityId().toString() + " " + this.getCPUNumber() + " " + this.getRamSize() + " " + this.isRented() + " " + this.getActualRentalPrice();
    }
}
