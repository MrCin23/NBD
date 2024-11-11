package org.example;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key="_clazz", value="standard")
public class Standard extends ClientType{

        public Standard() {
        super();
        this.maxRentedMachines = 10;
        this.name = "Standard";
    }

    @BsonCreator
    public Standard(@BsonProperty("_id") MongoUUID uuid,
                 @BsonProperty("maxRentedMachines") int maxRentedMachines,
                 @BsonProperty("name") String name){
        super();
        this.getEntityId().setUuid(uuid.getUuid());
        this.maxRentedMachines = maxRentedMachines;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Standard" + this.getClass().getSimpleName();
    }
}
