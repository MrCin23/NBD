package org.example.repository;

import org.example.exception.RedisConnectionError;
import org.example.model.MongoUUID;
import org.example.model.Rent;

import java.time.LocalDateTime;
import java.util.List;

public interface RentDataSource {
    void add(Rent rent) throws RedisConnectionError;

    void remove(MongoUUID uuid) throws RedisConnectionError;

    Rent getRentByID(MongoUUID uuid) throws RedisConnectionError;

    void endRent(MongoUUID uuid, LocalDateTime endTime) throws RedisConnectionError;

    List<Rent> getRents() throws RedisConnectionError;

    long size() throws RedisConnectionError;
}
