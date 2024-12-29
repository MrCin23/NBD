package org.example.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.Client;

import java.util.List;
import java.util.UUID;

@Dao
public interface ClientDao {
    @Insert
    void create(Client client);

    @Query("select * from clients where clientID = :clientID")
    Client findById(UUID clientID);

    @Query("select * from clients")
    PagingIterable<Client> findAll();

    @Update
    void update(Client client);

    @Delete
    void delete(Client client);
}
