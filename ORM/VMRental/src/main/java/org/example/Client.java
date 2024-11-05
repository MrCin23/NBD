package org.example;


import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Getter
public class Client implements Serializable {
    @BsonProperty("clientID")
    private UUID clientID;
    @Setter
    @BsonProperty("firstName")
    private String firstName;
    @Setter
    @BsonProperty("surname")
    private String surname;
    @Setter
    @BsonProperty("emailAddress")
    private String emailAddress;
    @Setter
    @BsonProperty("clientType")
    private ClientType clientType;



    public String toString() {
        return "Client: " + getFirstName() + " " + getSurname() + ", " + getEmailAddress() + " " + getClientType().toString();
    }

    public Client(String firstName, String surname, String emailAddress, ClientType clientType) {
        this.clientID = UUID.randomUUID();
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
    }

    @BsonCreator
    public Client(@BsonProperty("clientID") UUID clientID, @BsonProperty("firstName") String firstName,
                  @BsonProperty("surname") String surname, @BsonProperty("emailAddress") String emailAddress,
                  @BsonProperty("clientTypeID") UUID clientTypeID, @BsonProperty("name") String name, @BsonProperty("maxRentedMachines") int maxRentedMachines) {
                  //@BsonProperty("clientType") ClientType clientType) { //TODO Tak mamy zrobić potem
        this.clientID = clientID;
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        if(Objects.equals(name, "Admin")) {
            this.clientType = new Admin();
        }
        else {
            this.clientType = new Standard();
        }
    }
}
