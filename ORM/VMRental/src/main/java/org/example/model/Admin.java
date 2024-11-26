package org.example.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import java.util.UUID;

@BsonDiscriminator(key="_clazz", value="admin")
public class Admin extends ClientType {

    public Admin() {
        super(new MongoUUID(UUID.randomUUID()), 10, "Admin");
    }

    @JsonbCreator
    public Admin(@JsonbProperty("_id") MongoUUID uuid,
                 @JsonbProperty("maxRentedMachines") int maxRentedMachines,
                 @JsonbProperty("name") String name) {
        super(uuid, maxRentedMachines, name);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + super.toString();
    }
}
