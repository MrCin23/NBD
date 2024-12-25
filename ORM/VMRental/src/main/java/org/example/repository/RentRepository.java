package org.example.repository;

import org.example.model.Rent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RentRepository {
    List<Rent> rents;

    public RentRepository() {
        rents = new ArrayList<Rent>();
    }

    public void endRent(long id, LocalDateTime endTime){
        if(endTime == null){
            endTime = LocalDateTime.now();
        }
        Rent rent = getRentByID(id);
        rent.endRent(endTime);
    }

    public void update(long id, Map<String, Object> fieldsToUpdate) {
        throw new RuntimeException("Not implemented yet");
    }

    public void add(Rent rent) {
        throw new RuntimeException("Not implemented yet");
    }

    public long size(boolean active) {
        throw new RuntimeException("Not implemented yet");
    }

    public long size() {
        throw new RuntimeException("Not implemented yet");
    }

    public List<Rent> getRents(boolean active) {
        throw new RuntimeException("Not implemented yet");
    }

    public List<Rent> getRents() {
        throw new RuntimeException("Not implemented yet");
    }

    public Rent getRentByID(long ID) {
        throw new RuntimeException("Not implemented yet");
    }
}
