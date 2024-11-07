package org.example;


import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;


@Getter
public class Client extends AbstractEntityMgd {
//    @BsonProperty("clientID")
//    private UUID clientID;
    @Setter
    @BsonProperty("firstName")
    private String firstName;
    @Setter
    @BsonProperty("surname")
    private String surname;
    @Setter
    @BsonProperty("emailAddress")
    private String emailAddress;
//TODO    @Setter
//    private ClientType clientType;



    public String toString() {
        return "";//todo"Client: " + getFirstName() + " " + getSurname() + ", " + getEmailAddress() + " " + getClientType().toString();
    }

    public Client(String firstName, String surname, String emailAddress, ClientType clientType) {
        super(new MongoUUID(UUID.randomUUID()));
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
//TODO        this.clientType = clientType;
    }

    @BsonCreator
    public Client(@BsonProperty("_id") MongoUUID clientID, @BsonProperty("firstName") String firstName,
                  @BsonProperty("surname") String surname, @BsonProperty("emailAddress") String emailAddress) {
//TODO                  @BsonProperty("clientTypeID") UUID clientTypeID, @BsonProperty("name") String name, @BsonProperty("maxRentedMachines") int maxRentedMachines) {
                  //@BsonProperty("clientType") ClientType clientType) { //TODO Tak mamy zrobić potem
        super(clientID);
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
//TODO        if(Objects.equals(name, "Admin")) {
//            this.clientType = new Admin();
//        }
//        else {
//            this.clientType = new Standard();
//        }
    }
}
