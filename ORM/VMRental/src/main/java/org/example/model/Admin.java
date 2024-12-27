package org.example.model;

public class Admin extends ClientType{

    public Admin() {
        this.maxRentedMachines = 10;
    }

    @Override
    public String toString() {
        return "Admin";
    }
}
