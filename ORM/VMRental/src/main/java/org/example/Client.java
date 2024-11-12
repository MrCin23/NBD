package org.example;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Setter
@Getter
public class Client extends AbstractEntityMgd {
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("surname")
    private String surname;
    @BsonProperty("emailAddress")
    private String emailAddress;
    @BsonProperty("clientType")
    private ClientType clientType;

    public String toString() {
        return "Client: " + getFirstName() + " " + getSurname() + ", " + getEmailAddress() + " " + getClientType().toString() + " UUID: " + this.getEntityId().toString();
    }

    public Client(String firstName, String surname, String emailAddress, ClientType clientType) {
        super(new MongoUUID(UUID.randomUUID()));
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
    }

    @BsonCreator
    public Client(@BsonProperty("_id") MongoUUID clientID, @BsonProperty("firstName") String firstName,
                  @BsonProperty("surname") String surname, @BsonProperty("emailAddress") String emailAddress,
                  @BsonProperty("clientType") ClientType clientType) {

        super(clientID);
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
    }
}
