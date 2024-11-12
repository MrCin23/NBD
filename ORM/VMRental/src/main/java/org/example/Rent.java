package org.example;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class Rent extends AbstractEntityMgd {
    @BsonProperty("client")
    Client client;
    @BsonProperty("vMachine")
    VMachine vMachine;
    @BsonProperty("beginTime")
    LocalDateTime beginTime;
    @BsonProperty("endTime")
    LocalDateTime endTime;
    @BsonProperty("rentCost")
    double rentCost;

    public Rent() {
        super(new MongoUUID(UUID.randomUUID()));
    }

    public Rent(Client client, VMachine vMachine, LocalDateTime beginTime) {
        super(new MongoUUID(UUID.randomUUID()));
        if(!vMachine.isRented()) {
            this.client = client;
            this.vMachine = vMachine;
            beginRent(beginTime);
        }
        else {
            throw new RuntimeException("This machine is already rented");
        }
    }

    @BsonCreator
    public Rent(@BsonProperty("_id") MongoUUID uuid,@BsonProperty("client") Client client, @BsonProperty("vMachine") VMachine vMachine,
                @BsonProperty("beginTime") LocalDateTime beginTime, @BsonProperty("endTime") LocalDateTime endTime, @BsonProperty("rentCost") double rentCost) {
        super(uuid);
        this.client = client;
        this.vMachine = vMachine;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.rentCost = rentCost;
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

    @Override
    public String toString() {
        return "Rent{" +
                "client=" + client +
                ", vMachine=" + vMachine +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", rentCost=" + rentCost +
                '}';
    }

    public VMachine getvMachine() {
        return vMachine;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getRentCost() {
        return rentCost;
    }

}
