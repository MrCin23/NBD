package org.example.repository;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.model.Rent;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;

@NoArgsConstructor
@AllArgsConstructor
public class RentRedisRepository {
    private static final JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
    private static final JedisPooled pool = new JedisPooled(new HostAndPort("localhost", 6379), clientConfig);
    private final Jsonb jsonb = JsonbBuilder.create();

    public void add(Rent rent) {
        //TODO
    }

    public long size() {
        return 0;//TODO
    }



}
