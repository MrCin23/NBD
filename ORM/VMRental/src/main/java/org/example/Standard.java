package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Standard extends ClientType{

    public Standard() {
        this.maxRentedMachines = 3;
    }

    public String toString() {
        return "Standard";
    }
}
