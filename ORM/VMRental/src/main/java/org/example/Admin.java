package org.example;

import jakarta.persistence.Entity;

@Entity
public class Admin extends ClientType{

    public Admin() {
        this.maxRentedMachines = 10;
    }

    public String toString() {
        return "Admin";
    }
}
