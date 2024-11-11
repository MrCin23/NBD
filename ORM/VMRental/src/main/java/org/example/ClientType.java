package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
//@BsonDiscriminator(key="_clazz")
public class ClientType extends AbstractEntityMgd {

    @BsonProperty("maxRentedMachines")
    protected int maxRentedMachines;

    @BsonProperty("name")
    protected String name;

//    protected ClientType() {
//        super(new MongoUUID(UUID.randomUUID()));
//        this.name = getClass().getSimpleName();
//    }

    @BsonCreator
    public ClientType(@BsonProperty("_id") MongoUUID uuid,
                      @BsonProperty("maxRentedMachines") int maxRentedMachines,
                      @BsonProperty("name") String name){
        super(uuid);
        this.maxRentedMachines = maxRentedMachines;
        this.name = name;
    }

    public String toString() {
        return "Class: " + this.getClass().getSimpleName() + " " + this.getMaxRentedMachines() + " " + this.getName();
    }
}
