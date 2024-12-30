package org.example.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.*;
import org.example.consts.ClientConsts;
import org.example.consts.DBConsts;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(defaultKeyspace = DBConsts.KEYSPACE)
@CqlName(ClientConsts.TABLE_STRING)
@PropertyStrategy(getterStyle = GetterStyle.JAVABEANS)
@NamingStrategy(convention = NamingConvention.LOWER_CAMEL_CASE)
public class Client {
    @PartitionKey
    @CqlName(ClientConsts.UUID_STRING)
    private UUID clientID;
    @CqlName(ClientConsts.NAME_STRING)
    private String firstName;
    @CqlName(ClientConsts.SURNAME_STRING)
    private String surname;
    @CqlName(ClientConsts.EMAIL_STRING)
    private String emailAddress;
    @Getter

    // obrzydliwy gownokod, naprawic!!!!!!!!!!!!!!!
    @ClusteringColumn
    @CqlName(ClientConsts.TYPE_STRING)
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
