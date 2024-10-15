package org.example;

import jakarta.persistence.Entity;

@Entity
public class Standard extends ClientType{

    public Standard() {
        this.maxRentedMachines = 3;
    }

    public Standard(long id) {
        super(id);
        this.maxRentedMachines = 3;
    }

    public String toString() {
        return "Standard";
    }
}
