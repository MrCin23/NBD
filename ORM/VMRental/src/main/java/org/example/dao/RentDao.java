package org.example.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.AppleArch;
import org.example.model.Rent;
import org.example.model.x86;
import org.example.provider.RentProvider;
import org.example.provider.VMachineProvider;

import java.util.UUID;

@Dao
public interface RentDao {
    @StatementAttributes(consistencyLevel = "QUORUM")//todo sprawdzic czy to dobrze
    @QueryProvider(providerClass = RentProvider.class, entityHelpers = {Rent.class})
    void create(Rent rent);
//
//    @Select
//    Rent findById(UUID id);
//
//    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)//todo sprawdzic czy to dobrze
//    @QueryProvider(providerClass = RentProvider.class, entityHelpers = {Rent.class})
//    void endRentById(UUID id);
//    void findByClientId(UUID clientId);
//
//    void findByVMachineId(UUID uuid);
}
