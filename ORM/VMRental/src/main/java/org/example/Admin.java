package org.example;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(value="admin", key="_clazz")
public class Admin extends ClientType{

    public Admin() {
        super();
        this.maxRentedMachines = 10;
        this.name = "Admin";
    }

    @BsonCreator
    public Admin(@BsonProperty("_id") MongoUUID uuid,
                      @BsonProperty("maxRentedMachines") int maxRentedMachines,
                      @BsonProperty("name") String name){
        super();
        this.getEntityId().setUuid(uuid.getUuid());
        this.maxRentedMachines = maxRentedMachines;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Admin " + this.getClass().getSimpleName();
    }
}
