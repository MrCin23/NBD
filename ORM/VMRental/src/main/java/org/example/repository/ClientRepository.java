package org.example.repository;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.typesafe.config.ConfigException;
import org.example.DBConnection;
import org.example.model.Client;
import org.example.model.ClientType;

import java.util.*;

public class ClientRepository {
    List<Client> clients;
    private final DBConnection db = DBConnection.getInstance();
    public ClientRepository() {
        clients = new ArrayList<Client>();
        SimpleStatement createClients = SchemaBuilder.createTable(CqlIdentifier.fromCql("clients"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("clientID"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("clientType"), DataTypes.TEXT)
                .withColumn("firstName", DataTypes.TEXT)
                .withColumn("surname", DataTypes.TEXT)
                .withColumn("emailAddress", DataTypes.TEXT)
                .withClusteringOrder(CqlIdentifier.fromCql("clientType"), ClusteringOrder.ASC)
                .build();
        db.initSession();
        db.getSession().execute(createClients);
        clients = getClients();
    }

    public void update(UUID uuid, Map<String, Object> fieldsToUpdate) {
        throw new RuntimeException("Not implemented yet");
    }

    public void add(Client client) {

    }

    public void remove(Client client) {
        throw new RuntimeException("Not implemented yet");
    }

    public long size() {
        throw new RuntimeException("Not implemented yet");
    }

    public List<Client> getClients() {
        List<Client> clientList = new ArrayList<>();
        ResultSet resultSet = db.getSession().execute(QueryBuilder.selectFrom("clients").all().build());
        for (Row row : resultSet) {
            String clientTypeString = row.getString("clientType");
            try {
                assert clientTypeString != null;
                ClientType clientType = ClientType.fromString(clientTypeString);
                UUID clientID = UUID.fromString(Objects.requireNonNull(row.getString("clientID")));
                String firstName = row.getString("firstName");
                String surname = row.getString("surname");
                String emailAddress = row.getString("emailAddress");
                Client client = new Client(clientID, firstName, surname, emailAddress, clientType);
                clientList.add(client);
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new RuntimeException("Error while loading clients: " + e);
            }
        }
        return clientList;
    }

    public Client getClientByID(long ID) {
        throw new RuntimeException("Not implemented yet");
    }
}
