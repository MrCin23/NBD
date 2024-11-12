package org.example;


import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
public abstract class VMachine extends AbstractEntityMgd {

    @BsonProperty("CPUNumber")
    private int CPUNumber;
    @BsonProperty("ramSize")
    private String ramSize;
    @BsonProperty("isRented")
    private boolean isRented;
    @BsonProperty("actualRentalPrice")
    protected float actualRentalPrice;

    public VMachine(int CPUNumber, String ramSize, boolean isRented) {
        super(new MongoUUID(UUID.randomUUID()));
        this.CPUNumber = CPUNumber;
        this.ramSize = ramSize;
        this.isRented = isRented;
    }

    public VMachine() {
        super(new MongoUUID(UUID.randomUUID()));
    }

    @BsonCreator
    public VMachine(@BsonProperty("_id") MongoUUID uuid, @BsonProperty("CPUNumber") int CPUNumber,
                    @BsonProperty("ramSize") String ramSize, @BsonProperty("isRented") boolean isRented, @BsonProperty("actualRentalPrice") float actualRentalPrice) {
        super(uuid);
        this.CPUNumber = CPUNumber;
        this.ramSize = ramSize;
        this.isRented = isRented;
        this.actualRentalPrice = actualRentalPrice;
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


