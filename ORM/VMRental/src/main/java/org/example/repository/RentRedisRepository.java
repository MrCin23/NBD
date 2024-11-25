package org.example.repository;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.model.MongoUUID;
import org.example.model.Rent;
import redis.clients.jedis.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.util.*;


@AllArgsConstructor
public class RentRedisRepository {
    Properties configFile = new Properties();
    private static final JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
    private JedisPool pool;
    private final Jsonb jsonb = JsonbBuilder.create();
    private RentRepository rentRepository;

    public RentRedisRepository(){
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream("config.properties"));
            pool = new JedisPool(new JedisPoolConfig(), configFile.getProperty("redisUrl"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        rentRepository = new RentRepository();
    }


    /**
     * Funkcja dodaje wypożyczenie jeśli nie znajduje się w cache'u.
     * Gdy znajduje się już wypożyczenie o tym kluczu, funkcja je nadpisuje (aktualizuje)
     *
     * @param rent obiekt wypożyczenia
     */
    public void add(Rent rent) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(rent.getEntityId().getUuid().toString(), jsonb.toJson(rent));
            jedis.disconnect();
        }
        catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }
    }

    public void remove(Rent rent) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(rent.getEntityId().getUuid().toString());
            jedis.disconnect();
        }
        catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }
    }

    public Rent getRent(MongoUUID uuid) {
        try (Jedis jedis = pool.getResource()) {
            System.out.println("Key exists: " + jedis.exists(uuid.getUuid().toString()));
            System.out.println("Key type: " + jedis.type(uuid.getUuid().toString()));
            String rentString = jedis.get(uuid.getUuid().toString());
            jedis.disconnect();
            return jsonb.fromJson(rentString, Rent.class);

        }
        catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }
    }

    public void addAllRentsFromMongo() {
        try (Jedis jedis = pool.getResource()) {
            jedis.flushAll();
            List<Rent> rents = rentRepository.getRents();
            for(Rent rent : rents) {
                jedis.set(rent.getEntityId().getUuid().toString(), jsonb.toJson(rent));
            }
            jedis.disconnect();
        }
        catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }

    }

    public long size() {
        return 0;//TODO
    }




}
