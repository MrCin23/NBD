package org.example.model;


import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;


import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
public class Client extends AbstractEntityMgd {
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("surname")
    private String surname;
    @BsonProperty("emailAddress")
    private String emailAddress;
    @BsonProperty("clientType")
    private ClientType clientType;
    @BsonProperty("currentRents")
    private int currentRents;

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", clientType=" + clientType.toString() +
                ", currentRents=" + currentRents +
                //", uuid = " + super.getEntityId().getUuid().toString() +
                '}';
    }

    public Client(String firstName, String surname, String emailAddress, ClientType clientType) {
        super(new MongoUUID(UUID.randomUUID()));
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
        this.currentRents = 0;
    }

    @BsonCreator
    @JsonbCreator
    public Client(@BsonProperty("_id") @JsonbProperty("_id") MongoUUID clientID,
                  @BsonProperty("firstName") @JsonbProperty("firstName") String firstName,
                  @BsonProperty("surname") @JsonbProperty("surname") String surname,
                  @BsonProperty("emailAddress") @JsonbProperty("emailAddress") String emailAddress,
                  @BsonProperty("clientType") @JsonbProperty("clientType") ClientType clientType,
                  @BsonProperty("currentRents") @JsonbProperty("currentRents") int currentRents) {

        super(clientID);
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
        this.currentRents = currentRents;
    }

}
