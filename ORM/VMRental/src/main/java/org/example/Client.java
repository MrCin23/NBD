package org.example;

import jakarta.persistence.*;

import java.text.MessageFormat;
import java.util.UUID;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID ClientID;

    private String firstName;
    private String surname;
    private String emailAddress;
    @ManyToOne
    private ClientType clientType;


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

    public Client(String firstName, String surname, String emailAddress) {
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
    }

    public Client(UUID clientID, String firstName, String surname, String emailAddress) {
        ClientID = clientID;
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
    }
}
