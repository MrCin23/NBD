package org.example.model;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(defaultKeyspace = "vmrental")
@CqlName("clients")
@PropertyStrategy(getterStyle = GetterStyle.JAVABEANS)
public class Client {
    private UUID clientID;
    private String firstName;
    private String surname;
    private String emailAddress;
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
}
