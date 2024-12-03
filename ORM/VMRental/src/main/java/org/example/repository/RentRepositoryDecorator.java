package org.example.repository;

import jakarta.validation.constraints.Null;
import lombok.Getter;
import org.example.exception.RedisConnectionError;
import org.example.model.MongoUUID;
import org.example.model.Rent;
import redis.clients.jedis.Jedis;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;

public class RentRepositoryDecorator implements RentDataSource{
    private final RentRepository rentRepository;
    //DELETE THIS LATER
    @Getter
    private final RentRedisRepository rentRedisRepository;

    public RentRepositoryDecorator(RentRepository rentRepository, RentRedisRepository rentRedisRepository) {
        this.rentRepository = rentRepository;
        this.rentRedisRepository = rentRedisRepository;
    }

    @Override
    public void add(Rent rent) {
        try {
            rentRedisRepository.add(rent);
            rentRepository.add(rent);
        } catch (NullPointerException | RedisConnectionError e) {
            rentRepository.add(rent);
        }
    }

    @Override
    public void remove(MongoUUID uuid) {
        try {
            rentRedisRepository.remove(uuid);
        } catch (NullPointerException | RedisConnectionError e) {
            System.out.println("Redis disconnected. Removing from cache is not possible");
        }
    }

    @Override
    public Rent getRentByID(MongoUUID uuid) {
        try {
            return rentRedisRepository.getRentByID(uuid);
        } catch (NullPointerException | RedisConnectionError e) {
            return rentRepository.getRentByID(uuid);
        }
    }

    @Override
    public void endRent(MongoUUID uuid, LocalDateTime endTime) {
        try {
            rentRedisRepository.endRent(uuid, endTime);
            rentRepository.endRent(uuid, endTime);
        } catch (NullPointerException | RedisConnectionError e){
            rentRepository.endRent(uuid, endTime);
        }
    }

    @Override
    public List<Rent> getRents() {
        try {
            return rentRedisRepository.getRents();
        } catch (NullPointerException | RedisConnectionError e) {
            return rentRepository.getRents();
        }
    }

    @Override
    public long size() {
        return rentRepository.size();
    }

    public long cacheSize() {
        try {
            return rentRedisRepository.size();
        } catch (NullPointerException | RedisConnectionError e) {
            System.out.println("Redis disconnected. Cache size cannot be determined");
            return -1;
        }
    }

    public void addAllRentsFromMongo() {
        try {
            rentRedisRepository.clearAllCache();
            List<Rent> rents = rentRepository.getRents();
            for(Rent rent : rents) {
                rentRedisRepository.add(rent);
            }
        }
        catch (NullPointerException | RedisConnectionError e) {
            System.out.println("Redis disconnected. Adding rents to cache is not possible");
        }
    }

    public void dropAndCreate(){
        rentRepository.dropAndCreate();
    }

    public void clearAllCache(){
        try {
            rentRedisRepository.clearAllCache();
        } catch (NullPointerException | RedisConnectionError e) {
            System.out.println("Redis disconnected. Cache cannot be cleared");
        }
    }
}
