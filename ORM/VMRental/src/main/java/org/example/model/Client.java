package org.example.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(defaultKeyspace = "vmrental")
@CqlName("clients")
@PropertyStrategy(getterStyle = GetterStyle.JAVABEANS)
@NamingStrategy(convention = NamingConvention.LOWER_CAMEL_CASE)
public class Client {
    @PartitionKey
    @CqlName("clientID")
    private UUID clientID;
    @CqlName("firstName")
    private String firstName;
    private String surname;
    @CqlName("emailAddress")
    private String emailAddress;
    @Getter

    // obrzydliwy gownokod, naprawic!!!!!!!!!!!!!!!
    @ClusteringColumn
    @CqlName("clientType")
    private String clientTypeDb;
    @Getter
    @Transient
    private ClientType clientType;
    //////////////////////////////////////

    public String toString() {
        return "Client: " + getFirstName() + " " + getSurname() + ", " + getEmailAddress() + " " + getClientType().toString();
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
        this.clientTypeDb = clientType != null ? clientType.toString() : null;
    }

    public void setClientTypeDb(String clientTypeDb) {
        this.clientTypeDb = clientTypeDb;
        this.clientType = clientTypeDb != null ? ClientType.fromString(clientTypeDb) : null;
    }

    public Client(String firstName, String surname, String emailAddress, ClientType clientType) {
        this.clientID = UUID.randomUUID();
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.setClientType(clientType);
    }

    public Client(UUID id, String firstName, String surname, String emailAddress, ClientType clientType) {
        this.clientID = id;
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.setClientType(clientType);
    }
}
