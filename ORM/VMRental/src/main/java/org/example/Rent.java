package org.example;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.UUID;
@Entity
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID rentID;
    @ManyToOne
    Client client;
    @ManyToOne
    VMachine vMachine;
    Time beginTime;
    Time endTIme;
    double rentCost;

    //Methods
    public void beginRent() {
        //TODO
    }

    public Rent(UUID rentID, Client client, VMachine vMachine, Time beginTime) {
        this.rentID = rentID;
        this.client = client;
        this.vMachine = vMachine;
        this.beginTime = beginTime;
    }

    public UUID getRentID() {
        return rentID;
    }

    public void setRentID(UUID rentID) {
        this.rentID = rentID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public VMachine getvMachine() {
        return vMachine;
    }

    public void setvMachine(VMachine vMachine) {
        this.vMachine = vMachine;
    }

    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public Time getEndTIme() {
        return endTIme;
    }

    public void setEndTIme(Time endTIme) {
        this.endTIme = endTIme;
    }

    public double getRentCost() {
        return rentCost;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }
}
