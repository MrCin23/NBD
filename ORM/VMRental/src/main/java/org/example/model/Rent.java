package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
public class Rent extends AbstractEntityMgd {
    @BsonProperty("client")
    private Client client;
    @Getter
    @BsonProperty("vMachine")
    private VMachine vMachine;
    @Getter
    @BsonProperty("beginTime")
    private LocalDateTime beginTime;
    @Getter
    @BsonProperty("endTime")
    private LocalDateTime endTime;
    @Getter
    @BsonProperty("rentCost")
    private double rentCost;
    @BsonProperty("rentalName")
    private String rentalName;

    public Rent() {
        super(new MongoUUID(UUID.randomUUID()));
    }

    public Rent(Client client, VMachine vMachine, LocalDateTime beginTime) {
        super(new MongoUUID(UUID.randomUUID()));
        if(vMachine.isRented() == 0) {
            this.client = client;
            this.vMachine = vMachine;
            beginRent(beginTime);
            this.rentalName = "VMRental";
        }
        else {
            throw new RuntimeException("This machine is already rented");
        }
    }

    @BsonCreator
    public Rent(@BsonProperty("_id") MongoUUID uuid,@BsonProperty("client") Client client, @BsonProperty("vMachine") VMachine vMachine,
                @BsonProperty("beginTime") LocalDateTime beginTime, @BsonProperty("endTime") LocalDateTime endTime, @BsonProperty("rentCost") double rentCost, @BsonProperty("rentalName") String rentalName) {
        super(uuid);
        this.client = client;
        this.vMachine = vMachine;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.rentCost = rentCost;
        this.rentalName = rentalName;
    }

    //Methods
    public void beginRent(LocalDateTime beginTime) {
        if(this.beginTime == null && getVMachine().isRented()==0){
            this.setBeginTime(Objects.requireNonNullElseGet(beginTime, LocalDateTime::now));
            vMachine.setIsRented(vMachine.getIsRented()+1);
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
            this.getVMachine().setRented(0);
        }
        else {
            throw new RuntimeException("endRent() called twice");
        }
    }

    public void calculateRentalPrice() {
        Duration d = Duration.between(beginTime, endTime);
        int days = (int) d.toDays() + 1;
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
                ", rentalName='" + rentalName  +
                '}';
    }

}
