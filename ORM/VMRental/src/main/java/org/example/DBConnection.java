package org.example;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.internal.mapper.processor.util.generation.PropertyType;
import lombok.Getter;
import org.example.codec.ClientTypeCodec;
import org.example.model.ClientType;

import java.net.InetSocketAddress;

@Getter
public class DBConnection {
    private static DBConnection instance;
    private CqlSession session;

    private DBConnection() {}

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public void initSession() {
        if (session == null) {
            session = CqlSession.builder()
                    .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                    .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                    .addTypeCodecs(new ClientTypeCodec())
                    .withLocalDatacenter("dc1")
                    .withAuthCredentials("nbd", "nbd")
                    .withKeyspace(CqlIdentifier.fromCql("vmrental"))
                    .build();
        }
    }

    public void createKeyspace() {
        if (session != null) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS vmrental with replication = {'class': 'SimpleStrategy', 'replication_factor': 2}");
        } else {
            throw new IllegalStateException("Session is not initialized. Call initSession() first");
        }
    }

    public void createClientTable() {
        SimpleStatement createClients = SchemaBuilder.createTable(CqlIdentifier.fromCql("clients"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("clientID"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("clientType"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("firstName"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("surname"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("emailAddress"), DataTypes.TEXT)
                .withClusteringOrder(CqlIdentifier.fromCql("clientType"), ClusteringOrder.ASC)
                .build();
        session.execute(createClients);
    }

    public void dropClientTable() {
        SimpleStatement dropClients = SchemaBuilder.dropTable(CqlIdentifier.fromCql("clients"))
                .ifExists()
                .build();
        session.execute(dropClients);
    }

    public void createRentTable() {
        throw new RuntimeException("Not implemented yet");
    }

    public void createVMachineTable() {
        SimpleStatement createVMachines = SchemaBuilder.createTable(CqlIdentifier.fromCql("vmachines"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("uuid"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("CPUNumber"), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql("ramSize"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("rented"), DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql("discriminator"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("cpumanufacturer"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("actualRentalPrice"), DataTypes.FLOAT)
                .withClusteringOrder(CqlIdentifier.fromCql("CPUNumber"), ClusteringOrder.ASC)
                .build();
        session.execute(createVMachines);
    }

    public void dropVMachineTable() {
        SimpleStatement dropVMachines = SchemaBuilder.dropTable(CqlIdentifier.fromCql("vmachines"))
                .ifExists()
                .build();
        session.execute(dropVMachines);
    }

    public void dropKeyspace() {
        if (session != null) {
            session.execute("DROP KEYSPACE IF EXISTS vmrental");
        } else {
            throw new IllegalStateException("Session is not initialized. Call initSession() first");
        }
    }

    public void closeSession() {
        if (session != null) {
            session.close();
        } else {
            System.out.println("Session is not initialized. Nothing to close.");
        }
    }
}
