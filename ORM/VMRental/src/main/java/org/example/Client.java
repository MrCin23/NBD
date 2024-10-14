package org.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.text.MessageFormat;
import java.util.UUID;

@Entity
public class Client implements RepoElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID ClientID;
    @NotNull
    private String firstName;
    private String surname;
    @NotNull
    private String emailAddress;
    @ManyToOne
    private ClientType clientType;


    public UUID getClientID() {
        return ClientID;
    }

    //REPO TEMPLATE
    public UUID getID() {
        return ClientID;
    }

    public void setClientID(UUID clientID) {
        ClientID = clientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }


    public String toString() {
        return "Client: " + getFirstName() + " " + getSurname() + ", " + getEmailAddress() + " " + getClientType().toString();
    }

    //TODO zobaczyć czy aż tyle konstruktorów jest potrzebnych
    public Client() {
    }



    public Client(UUID clientID, String firstName, String surname, String emailAddress, ClientType clientType) {
        ClientID = clientID;
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
    }
}
