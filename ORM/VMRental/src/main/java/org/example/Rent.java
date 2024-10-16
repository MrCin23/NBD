package org.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
public class Rent implements RepoElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long rentID;
    @ManyToOne
    @NotNull
    Client client;
    @ManyToOne
    @NotNull
    VMachine vMachine;
    Time beginTime;
    Time endTime;
    double rentCost;

    public Rent() {

    }

    //Methods
    public void beginRent(Time beginTime) {
        if(this.beginTime == null && !(getvMachine().isRented())){
            if(beginTime == null)
            {
                this.setBeginTime(java.sql.Time.valueOf(LocalTime.now()));
            }
            this.setBeginTime(beginTime);
        }
        else if(beginTime == null){
            throw new RuntimeException("beginRent() called twice");
        }
        else {
            throw new RuntimeException("this VMachine is already rented");
        }
    }

    public void endRent(Time endTime) {
        if(this.endTime == null){
            if(endTime == null)
            {
                this.setEndTime(java.sql.Time.valueOf(LocalTime.now()));
            }
            this.setEndTime(endTime);
            this.getvMachine().setRented(false);
        }
        else {
            throw new RuntimeException("endRent() called twice");
        }
    }

    public Rent(long rentID, Client client, VMachine vMachine, Time beginTime) {
        if(!vMachine.isRented()) {
            this.rentID = rentID;
            this.client = client;
            this.vMachine = vMachine;
            beginRent(beginTime);
        }
        else {
            throw new RuntimeException("This machine is already rented");
        }
    }

    public double calculateRentalPrice() {
        //TODO add logic and code
        return 0;
    }

    public long getRentID() {
        return rentID;
    }

    //REPO TEMPLATE
    public long getID() {
        return rentID;
    }

    public void setRentID(long rentID) {
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

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTIme) {
        this.endTime = endTIme;
    }

    public double getRentCost() {
        return rentCost;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }
}
