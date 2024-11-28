package org.example.repository;

import org.example.model.MongoUUID;
import org.example.model.Rent;

import java.time.LocalDateTime;
import java.util.List;

public interface RentDataSource {
    void add(Rent rent);

    void remove(MongoUUID uuid);

    Rent getRentByID(MongoUUID uuid);

    void endRent(MongoUUID uuid, LocalDateTime endTime);

    List<Rent> getRents();

    long size();
}
