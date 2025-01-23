package org.example.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "_clazz")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AppleArch.class, name = "AppleArch"),
        @JsonSubTypes.Type(value = x86.class, name = "x86")
})
public abstract class VMachine extends AbstractEntityMgd {

    @BsonProperty("CPUNumber")
    private int CPUNumber;
    @BsonProperty("ramSize")
    private String ramSize;
    @BsonProperty("isRented")
    private int isRented;
    @BsonProperty("actualRentalPrice")
    protected float actualRentalPrice;

    public VMachine(int CPUNumber, String ramSize, int isRented) {
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
                    @BsonProperty("ramSize") String ramSize, @BsonProperty("isRented") int isRented) {
        super(uuid);
        this.CPUNumber = CPUNumber;
        this.ramSize = ramSize;
        this.isRented = isRented;
    }

    public int isRented() {
        return isRented;
    }

    public void setRented(int rented) {
        isRented = rented;
    }

    public float getActualRentalPrice() {
        return 0;
    }
};


