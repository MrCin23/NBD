package org.example.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.AppleArch;
import org.example.model.VMachine;
import org.example.model.x86;
import org.example.provider.VMachineGetByIdProvider;

import java.util.UUID;

@Dao
public interface VMachineDao {
    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @QueryProvider(providerClass = VMachineGetByIdProvider.class, entityHelpers = {AppleArch.class, x86.class})
    VMachine findById(UUID uuid);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = VMachineGetByIdProvider.class, entityHelpers = {AppleArch.class, x86.class})
    void create(VMachine vMachine);

    @Delete
    void delete(VMachine vMachine);

    //get all

    //update
}
