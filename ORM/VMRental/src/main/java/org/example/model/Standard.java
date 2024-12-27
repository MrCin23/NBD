package org.example.model;


public class Standard extends ClientType{

    public Standard() {
        this.maxRentedMachines = 3;
    }

    @Override
    public String toString() {
        return "Standard";
    }
}
