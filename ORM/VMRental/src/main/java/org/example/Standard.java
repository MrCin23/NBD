package org.example;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

public class Standard extends ClientType{

    public Standard() {
        super(new MongoUUID(UUID.randomUUID()), 3, "Standard");
    }



    @Override
    public String toString() {
        return "Standard" + this.getClass().getSimpleName();
    }
}
