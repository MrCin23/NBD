package org.example.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.AppleArch;
import org.example.model.VMachine;
import org.example.model.x86;
import org.example.provider.VMachineProvider;

import java.util.List;
import java.util.UUID;

@Dao
public interface VMachineDao {
    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @QueryProvider(providerClass = VMachineProvider.class, entityHelpers = {AppleArch.class, x86.class})
    VMachine findById(UUID uuid);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = VMachineProvider.class, entityHelpers = {AppleArch.class, x86.class})
    void create(VMachine vMachine);

    @Delete
    void delete(VMachine vMachine);

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)//todo sprawdzic czy to dobrze
    @QueryProvider(providerClass = VMachineProvider.class, entityHelpers = {AppleArch.class, x86.class})
    List<VMachine> getAll();

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)//todo sprawdzic czy to dobrze
    @QueryProvider(providerClass = VMachineProvider.class, entityHelpers = {AppleArch.class, x86.class})
    void update(VMachine vMachine);
}
