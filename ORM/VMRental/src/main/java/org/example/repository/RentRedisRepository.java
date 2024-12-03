package org.example.repository;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.AllArgsConstructor;
import org.example.exception.RedisConnectionError;
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

    public RentRedisRepository() throws RedisConnectionError {
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream("config.properties"));
            pool = new JedisPool(new JedisPoolConfig(), configFile.getProperty("redisUrl"));
            clearAllCache();
        }
        catch (Exception e) {
            System.out.println("redis disconnected");
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }

    public RentRedisRepository(String redisHost, int redisPort) throws RedisConnectionError {
        try {
            pool = new JedisPool(new JedisPoolConfig(), redisHost, redisPort);
            clearAllCache();
        } catch (Exception e) {
            System.out.println("Redis disconnected");
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }


    /**
     * Funkcja dodaje wypożyczenie jeśli nie znajduje się w cache'u.
     * Gdy znajduje się już wypożyczenie o tym kluczu, funkcja je nadpisuje (aktualizuje)
     *
     * @param rent obiekt wypożyczenia
     */
    public void add(Rent rent) throws RedisConnectionError {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(rent.getEntityId().getUuid().toString(), jsonb.toJson(rent));
        }
        catch (Exception e) {
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }

    public void remove(MongoUUID uuid) throws RedisConnectionError {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(uuid.getUuid().toString());
        }
        catch (Exception e) {
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }

    @Override
    public void endRent(MongoUUID uuid, LocalDateTime endTime) throws RedisConnectionError {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(uuid.getUuid().toString());
        }
        catch (Exception e) {
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }

    @Override
    public List<Rent> getRents() throws RedisConnectionError {
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
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }

    @Override
    public Rent getRentByID(MongoUUID uuid) throws RedisConnectionError {
        try (Jedis jedis = pool.getResource()) {
            String rentString = jedis.get(uuid.getUuid().toString());
            if (rentString.isEmpty()) {
                throw new RuntimeException("There is no rent for uuid: " + uuid.getUuid().toString());
            }
            return jsonb.fromJson(rentString, Rent.class);
        }
        catch (Exception e) {
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }

    @Override
    public long size() throws RedisConnectionError {
        try (Jedis jedis = pool.getResource()) {
            return jedis.dbSize();
        } catch (Exception e) {
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }

    public void clearCache() throws RedisConnectionError {
        try (Jedis jedis = pool.getResource()) {
            jedis.flushDB();
        } catch (Exception e) {
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }

    public void clearAllCache() throws RedisConnectionError {
        try (Jedis jedis = pool.getResource()) {
            jedis.flushAll();
        } catch (Exception e) {
            throw new RedisConnectionError("Redis disconnected", e);
        }
    }
}
