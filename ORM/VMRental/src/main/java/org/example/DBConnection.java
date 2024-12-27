package org.example;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import lombok.Getter;

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
