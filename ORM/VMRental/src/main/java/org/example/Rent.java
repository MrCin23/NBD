package org.example;

import java.io.Serializable;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class Rent  {
    long rentID;
    Client client;
    VMachine vMachine;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    double rentCost;

    public Rent() {

    }

    public Rent(Client client, VMachine vMachine, LocalDateTime beginTime) {
        if(!vMachine.isRented()) {
            this.client = client;
            this.vMachine = vMachine;
            beginRent(beginTime);
        }
        else {
            throw new RuntimeException("This machine is already rented");
        }
    }

    //Methods
    public void beginRent(LocalDateTime beginTime) {
        if(this.beginTime == null && !(getvMachine().isRented())){
            if(beginTime == null)
            {
                this.setBeginTime(LocalDateTime.now());
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

    public void endRent(LocalDateTime endTime) {
        if(this.endTime == null){
            if(endTime == null)
            {
                this.setEndTime(LocalDateTime.now());
            }
            this.setEndTime(endTime);
            this.calculateRentalPrice();
            this.getvMachine().setRented(false);
        }
        else {
            throw new RuntimeException("endRent() called twice");
        }
    }

    public void calculateRentalPrice() {
        Duration d = Duration.between(beginTime, endTime);
        int days = (int) d.toDays();
        this.rentCost = days * vMachine.getActualRentalPrice();
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

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTIme) {
        this.endTime = endTIme;
    }

    public double getRentCost() {
        return rentCost;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }
}
