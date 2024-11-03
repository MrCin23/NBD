package org.example;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class ClientMapping extends AbstractEntityMgd{

    @BsonCreator
    public ClientMapping(@BsonProperty("_id") MongoUUID entityID,
                         @BsonProperty("clientID") MongoUUID clientID,
                         @BsonProperty("firstName") String firstName,
                         @BsonProperty("surname") String surname,
                         @BsonProperty("emailAddress") String emailAddress,
                         @BsonProperty("clientType") ClientType clientType){
        super(entityID);
        this.clientID = clientID;
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
    }

    @BsonProperty("clientID")
    private MongoUUID clientID;
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("surname")
    private String surname;
    @BsonProperty("emailAddress")
    private String emailAddress;
    //TODO to pewnie ma być inaczej
    @BsonProperty("clientType")
    private ClientType clientType;

    public MongoUUID getClientID() {
        return clientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public ClientType getClientType() {
        return clientType;
    }
}
