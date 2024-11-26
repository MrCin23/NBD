package org.example.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "_clazz"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AppleArch.class, name = "applearch"),
        @JsonSubTypes.Type(value = x86.class, name = "x86")
})
@BsonDiscriminator(key = "_clazz")
@JsonbTypeInfo(key = "_clazz", value = {@JsonbSubtype(alias = "applearch", type = AppleArch.class), @JsonbSubtype(alias = "x86", type = x86.class)})
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
    @JsonbCreator
    public VMachine(@BsonProperty("_id") @JsonbProperty("_id") MongoUUID uuid,
                    @BsonProperty("CPUNumber") @JsonbProperty("CPUNumber") int CPUNumber,
                    @BsonProperty("ramSize") @JsonbProperty("ramSize") String ramSize,
                    @BsonProperty("isRented") @JsonbProperty("isRented") int isRented) {
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


