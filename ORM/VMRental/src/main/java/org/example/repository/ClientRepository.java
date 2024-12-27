package org.example.repository;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.DBConnection;
import org.example.model.Client;
import org.example.model.ClientType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientRepository {
    List<Client> clients;

    public ClientRepository() {
        clients = new ArrayList<Client>();
        SimpleStatement createClients = SchemaBuilder.createTable(CqlIdentifier.fromCql("clients"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("clientID"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("clientType"), DataTypes.custom("ClientType"))//this doesnt work
                .withColumn("firstName", DataTypes.TEXT)
                .withColumn("surname", DataTypes.TEXT)
                .withColumn("emailAddress", DataTypes.TEXT)
                .withClusteringOrder(CqlIdentifier.fromCql("clientType"), ClusteringOrder.ASC)
                .build();
        DBConnection db = new DBConnection();
        db.initSession();
        db.getSession().execute(createClients);
    }

    public void update(long id, Map<String, Object> fieldsToUpdate) {
        throw new RuntimeException("Not implemented yet");
    }

    public void add(Client client) {
        throw new RuntimeException("Not implemented yet");
    }

    public void remove(Client client) {
        throw new RuntimeException("Not implemented yet");
    }

    public long size() {
        throw new RuntimeException("Not implemented yet");
    }

    public List<Client> getClients() {
        throw new RuntimeException("Not implemented yet");
    }

    public Client getClientByID(long ID) {
        throw new RuntimeException("Not implemented yet");
    }
}
