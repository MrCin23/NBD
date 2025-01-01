package org.example.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import org.example.consts.DBConsts;
import org.example.consts.RentConsts;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(defaultKeyspace = DBConsts.KEYSPACE)
@CqlName(RentConsts.TABLE_VMACHINES_STRING)
@PropertyStrategy(getterStyle = GetterStyle.JAVABEANS)
@NamingStrategy(convention = NamingConvention.LOWER_CAMEL_CASE)
public class RentsByVMachine extends Rent {
    @CqlName(RentConsts.UUID_STRING)
    UUID rentID;
    @CqlName(RentConsts.CLIENT_UUID_STRING)
    UUID clientID;
    @Transient
    Client client;
    @PartitionKey
    @CqlName(RentConsts.VM_UUID_STRING)
    UUID vmID;
    @Transient
    VMachine vMachine;
    @CqlName(RentConsts.BEGIN_TIME_STRING)
    LocalDateTime beginTime;
    @CqlName(RentConsts.END_TIME_STRING)
    LocalDateTime endTime;
    @CqlName(RentConsts.RENT_COST_STRING)
    double rentCost;

    public RentsByVMachine() {
        super();
    }
    public RentsByVMachine(Client client, VMachine vMachine, LocalDateTime beginTime) {
        super(client, vMachine, beginTime);
    }
    public RentsByVMachine(UUID uuid, UUID clientID, UUID vmID, LocalDateTime beginTime, LocalDateTime endTime, double rentCost) {
        super(uuid, clientID, vmID, beginTime, endTime, rentCost);
    }
}
