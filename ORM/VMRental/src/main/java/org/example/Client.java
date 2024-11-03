package org.example;


public class Client  {
    private long clientID;
    private String firstName;
    private String surname;
    private String emailAddress;
    private ClientType clientType;
    int version;


    public long getclientID() {
        return clientID;
    }

    //REPO TEMPLATE
    public long getID() {
        return clientID;
    }

    public void setclientID(long clientID) {
        clientID = clientID;
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



    public Client(String firstName, String surname, String emailAddress, ClientType clientType) {
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
    }
}
