package org.example;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import lombok.Getter;

import java.net.InetSocketAddress;

@Getter
public class DBConnection {
    private CqlSession session;

    public void initSession() {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("nbd", "nbd")
                .withKeyspace(CqlIdentifier.fromCql("vmrental"))
                .build();
    }

    public void createSchema() {
        session.execute("CREATE KEYSPACE IF NOT EXISTS vmrental with replication = {'class': 'SimpleStrategy', 'replication_factor': 2}");
    }
}
