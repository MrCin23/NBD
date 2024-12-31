package org.example.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.DBConnection;
import org.example.consts.DBConsts;
import org.example.consts.RentConsts;
import org.example.dao.ClientDao;
import org.example.dao.VMachineDao;
import org.example.mapper.ClientMapper;
import org.example.mapper.ClientMapperBuilder;
import org.example.mapper.VMachineMapper;
import org.example.mapper.VMachineMapperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(defaultKeyspace = DBConsts.KEYSPACE)
@CqlName("rent")
@PropertyStrategy(getterStyle = GetterStyle.JAVABEANS)
@NamingStrategy(convention = NamingConvention.LOWER_CAMEL_CASE)
public class Rent {
    @PartitionKey //to raczej na pewno zle jest xd
    @CqlName(RentConsts.UUID_STRING)
    UUID rentID;
    @ClusteringColumn(0)
    @CqlName(RentConsts.CLIENT_UUID_STRING)
    UUID clientID;
    @Transient //?? idk if this is a good way to solve the problem
    Client client;
    @ClusteringColumn(1)
    @CqlName(RentConsts.VM_UUID_STRING)
    UUID vmID;
    @Transient //?? idk if this is a good way to solve the problem
    VMachine vMachine;
    @CqlName(RentConsts.BEGIN_TIME_STRING)
    LocalDateTime beginTime;
    @CqlName(RentConsts.END_TIME_STRING)
    LocalDateTime endTime;
    @CqlName(RentConsts.RENT_COST_STRING)
    double rentCost;

    public Rent(Client client, VMachine vMachine, LocalDateTime beginTime) {
        if(!vMachine.isRented()) {
            setClient(client);
            setvMachine(vMachine);
            this.rentID = UUID.randomUUID();
            beginRent(beginTime);
        }
        else {
            throw new RuntimeException("This machine is already rented");
        }
    }

    public Rent(UUID uuid, UUID clientID, UUID vmID, LocalDateTime beginTime, LocalDateTime endTime, double rentCost) {
        setClientID(clientID);
        setvmID(vmID);
        this.rentID = uuid;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public void setvMachine(VMachine vMachine) {
        this.vMachine = vMachine;
        this.vmID = vMachine.getUuid();
    }

    public void setvmID(UUID vmID) {
        this.vmID = vmID;
        VMachineMapper mapper = new VMachineMapperBuilder(DBConnection.getInstance().getSession()).build();
        VMachineDao dao = mapper.vMachineDao();
        this.vMachine = dao.findById(vmID);
    }

    public void setClient(Client client) {
        this.client = client;
        this.clientID = client.getClientID();
    }

    public void setClientID(UUID clientID) {
        this.clientID = clientID;
        ClientMapper mapper = new ClientMapperBuilder(DBConnection.getInstance().getSession()).build();
        ClientDao dao = mapper.clientDao();
        this.client = dao.findById(clientID);
    }

    //Methods
    public void beginRent(LocalDateTime beginTime) {
        if(this.beginTime == null && !(getVMachine().isRented())){
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
            this.getVMachine().setRented(false);
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
                "rentID=" + rentID +
                ", clientID=" + clientID +
                ", client=" + client +
                ", vmID=" + vmID +
                ", vMachine=" + vMachine +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", rentCost=" + rentCost +
                '}';
    }
}
