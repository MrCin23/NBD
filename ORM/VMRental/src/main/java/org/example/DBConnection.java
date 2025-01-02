package org.example;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import lombok.Getter;
import org.example.codec.ClientTypeCodec;
import org.example.codec.LocalDateTimeCodec;
import org.example.consts.ClientConsts;
import org.example.consts.DBConsts;
import org.example.consts.RentConsts;
import org.example.consts.VMConsts;

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
                    .addContactPoint(new InetSocketAddress(DBConsts.CASSANDRA1HOSTNAME, DBConsts.CASSANDRA1PORT))
                    .addContactPoint(new InetSocketAddress(DBConsts.CASSANDRA2HOSTNAME, DBConsts.CASSANDRA2PORT))
                    .addTypeCodecs(new ClientTypeCodec())
                    .addTypeCodecs(new LocalDateTimeCodec())
                    .withLocalDatacenter(DBConsts.DATACENTER)
                    .withAuthCredentials(DBConsts.USERNAME, DBConsts.PASSWORD)
                    .withKeyspace(DBConsts.KEYSPACE) //todo to za pierwszym razem na nowej maszynie komentować
                    .build();
        }
    }

    public void createKeyspace() {
        if (session != null) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " +
                    DBConsts.KEYSPACE +
                    " with replication = {'class': 'SimpleStrategy', 'replication_factor': 2}");
        } else {
            throw new IllegalStateException("Session is not initialized. Call initSession() first");
        }
    }

    public void createClientTable() {
        SimpleStatement createClients = SchemaBuilder.createTable(ClientConsts.TABLE)
                .ifNotExists()
                .withPartitionKey(ClientConsts.UUID, DataTypes.UUID)
                .withClusteringColumn(ClientConsts.TYPE, DataTypes.TEXT)
                .withColumn(ClientConsts.NAME, DataTypes.TEXT)
                .withColumn(ClientConsts.SURNAME, DataTypes.TEXT)
                .withColumn(ClientConsts.EMAIL, DataTypes.TEXT)
                .withClusteringOrder(ClientConsts.TYPE, ClusteringOrder.ASC)
                .build();
        session.execute(createClients);
    }

    public void dropClientTable() {
        SimpleStatement dropClients = SchemaBuilder.dropTable(ClientConsts.TABLE)
                .ifExists()
                .build();
        session.execute(dropClients);
    }

    public void createRentTables() {
        SimpleStatement createRentByClient = SchemaBuilder.createTable(RentConsts.TABLE_CLIENTS)
                .ifNotExists()
                .withPartitionKey(RentConsts.CLIENT_UUID, DataTypes.UUID)
                .withClusteringColumn(RentConsts.BEGIN_TIME, DataTypes.TIMESTAMP)
                .withColumn(RentConsts.UUID, DataTypes.UUID)
                .withColumn(RentConsts.END_TIME, DataTypes.TIMESTAMP)
                .withColumn(RentConsts.VM_UUID, DataTypes.UUID)
                .withColumn(RentConsts.RENT_COST, DataTypes.DOUBLE)
                .withClusteringOrder(RentConsts.BEGIN_TIME, ClusteringOrder.ASC)
                .build();
        SimpleStatement createRentByVMachine = SchemaBuilder.createTable(RentConsts.TABLE_VMACHINES)
                .ifNotExists()
                .withPartitionKey(RentConsts.VM_UUID, DataTypes.UUID)
                .withColumn(RentConsts.UUID, DataTypes.UUID)
                .withColumn(RentConsts.BEGIN_TIME, DataTypes.TIMESTAMP)
                .withColumn(RentConsts.END_TIME, DataTypes.TIMESTAMP)
                .withColumn(RentConsts.CLIENT_UUID, DataTypes.UUID)
                .withColumn(RentConsts.RENT_COST, DataTypes.DOUBLE)
                .build();
        session.execute(createRentByClient);
        session.execute(createRentByVMachine);
    }

    public void dropRentTables() {
        SimpleStatement dropByClient = SchemaBuilder.dropTable(RentConsts.TABLE_CLIENTS)
                .ifExists()
                .build();
        SimpleStatement dropByVMachine = SchemaBuilder.dropTable(RentConsts.TABLE_VMACHINES)
                .ifExists()
                .build();
        session.execute(dropByClient);
        session.execute(dropByVMachine);
    }

    public void createVMachineTable() {
        SimpleStatement createVMachines = SchemaBuilder.createTable(VMConsts.TABLE)
                .ifNotExists()
                .withPartitionKey(VMConsts.UUID, DataTypes.UUID)
                .withClusteringColumn(VMConsts.CPUNUMBER, DataTypes.INT)
                .withColumn(VMConsts.RAM, DataTypes.TEXT)
                .withColumn(VMConsts.RENTED, DataTypes.BOOLEAN)
                .withColumn(VMConsts.DISCRIMINATOR, DataTypes.TEXT)
                .withColumn(VMConsts.MANUFACTURER, DataTypes.TEXT)
                .withColumn(VMConsts.RENTALPRICE, DataTypes.FLOAT)
                .withClusteringOrder(VMConsts.CPUNUMBER, ClusteringOrder.ASC)
                .build();
        session.execute(createVMachines);
    }

    public void dropVMachineTable() {
        SimpleStatement dropVMachines = SchemaBuilder.dropTable(VMConsts.TABLE)
                .ifExists()
                .build();
        session.execute(dropVMachines);
    }

    public void dropKeyspace() {
        if (session != null) {
            session.execute("DROP KEYSPACE IF EXISTS " +
                    DBConsts.KEYSPACE);
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
