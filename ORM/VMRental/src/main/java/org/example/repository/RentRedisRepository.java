package org.example.repository;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.AllArgsConstructor;
import org.example.model.MongoUUID;
import org.example.model.Rent;
import redis.clients.jedis.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.time.LocalDateTime;
import java.util.*;


@AllArgsConstructor
public class RentRedisRepository implements RentDataSource{
    Properties configFile = new Properties();
    private static final JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
    private JedisPool pool;
    private final Jsonb jsonb = JsonbBuilder.create();

    public RentRedisRepository() {
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream("config.properties"));
            pool = new JedisPool(new JedisPoolConfig(), configFile.getProperty("redisUrl"));
            clearAllCache();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    public void remove(MongoUUID uuid) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(uuid.getUuid().toString());
            jedis.disconnect();
        }
        catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }
    }

    @Override
    public void endRent(MongoUUID uuid, LocalDateTime endTime) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(uuid.getUuid().toString());
            jedis.disconnect();
        }
        catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }
    }

    @Override
    public List<Rent> getRents() {
        try (Jedis jedis = pool.getResource()) {
            Set<String> keys = jedis.keys("*");
            List<Rent> rents = new ArrayList<>();
            for (String key : keys) {
                String rentJson = jedis.get(key);
                if (rentJson != null) {
                    rents.add(jsonb.fromJson(rentJson, Rent.class));
                }
            }
            return rents;
        } catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }
    }

    @Override
    public Rent getRentByID(MongoUUID uuid) {
        try (Jedis jedis = pool.getResource()) {
            String rentString = jedis.get(uuid.getUuid().toString());
            jedis.disconnect();
            if (rentString.isEmpty()) {
                throw new RuntimeException("There is no rent for uuid: " + uuid.getUuid().toString());
            }
            return jsonb.fromJson(rentString, Rent.class);
        }
        catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }
    }

    @Override
    public long size() {
        try (Jedis jedis = pool.getResource()) {
            return jedis.dbSize();
        } catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }
    }

    public void clearCache(){
        try (Jedis jedis = pool.getResource()) {
            jedis.flushDB();
        } catch (Exception e) {
            throw new RuntimeException("Redis connection error while clearing cache", e);
        }
    }

    public void clearAllCache(){
        try (Jedis jedis = pool.getResource()) {
            jedis.flushAll();
        } catch (Exception e) {
            throw new RuntimeException("Redis connection error while clearing cache", e);
        }
    }
}
