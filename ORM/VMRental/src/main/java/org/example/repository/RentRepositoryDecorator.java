package org.example.repository;

import org.example.model.MongoUUID;
import org.example.model.Rent;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.List;

public class RentRepositoryDecorator implements RentDataSource{
    private final RentRepository rentRepository;
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(MongoUUID uuid) {
        try {
            rentRedisRepository.remove(uuid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rent getRentByID(MongoUUID uuid) {
        try {
            return rentRedisRepository.getRentByID(uuid);
        } catch (Exception e) {
            return rentRepository.getRentByID(uuid);
        }
    }

    @Override
    public void endRent(MongoUUID uuid, LocalDateTime endTime) {
        rentRedisRepository.endRent(uuid, endTime);
        rentRepository.endRent(uuid, endTime);
    }

    @Override
    public List<Rent> getRents() {
        List<Rent> rents;
        try {
            rents = rentRedisRepository.getRents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(rents == null || rents.isEmpty()) {
            return rentRepository.getRents();
        }
        return rents;
    }

    @Override
    public long size() {
        return rentRepository.size();
    }

    public long cacheSize() {
        return rentRedisRepository.size();
    }

    public void addAllRentsFromMongo() {
        rentRedisRepository.clearAllCache();
        try {
            List<Rent> rents = rentRepository.getRents();
            for(Rent rent : rents) {
                rentRedisRepository.add(rent);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Redis connection error", e);
        }
    }
}
