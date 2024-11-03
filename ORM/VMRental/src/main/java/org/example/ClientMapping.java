package org.example;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

public class ClientMapping {

    @BsonCreator
    public ClientMapping(@BsonProperty("_id") UUID clientID,
                         @BsonProperty("firstName") String firstName,
                         @BsonProperty("surname") String surname,
                         @BsonProperty("emailAddress") String emailAddress,
                         @BsonProperty("clientType") ClientType clientType){
        this.clientID = clientID;
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
    }

    @BsonProperty("_id")
    private UUID clientID;
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("surname")
    private String surname;
    @BsonProperty("emailAddress")
    private String emailAddress;
    //TODO to pewnie ma być inaczej
    @BsonProperty("clientType")
    private ClientType clientType;
}
