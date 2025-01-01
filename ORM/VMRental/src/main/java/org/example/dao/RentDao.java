package org.example.dao;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.Rent;
import org.example.provider.RentProvider;

import java.util.List;
import java.util.UUID;

@Dao
public interface RentDao {
    @StatementAttributes(consistencyLevel = "QUORUM")//todo sprawdzic czy to dobrze
    @QueryProvider(providerClass = RentProvider.class)
    void create(Rent rent); //works

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @QueryProvider(providerClass = RentProvider.class)
    List<Rent> findAllByTable(CqlIdentifier table); //works

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @QueryProvider(providerClass = RentProvider.class)
    List<Rent> findByClientId(UUID clientId); //works

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @QueryProvider(providerClass = RentProvider.class)
    List<Rent> findByVMachineId(UUID clientId); //works

    @StatementAttributes(consistencyLevel = "QUORUM", pageSize = 100)
    @QueryProvider(providerClass = RentProvider.class)
    void endRent(Rent rent); //works
}
