package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client  {
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
