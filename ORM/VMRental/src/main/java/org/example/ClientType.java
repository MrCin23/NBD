package org.example;

import jakarta.persistence.*;

@Entity
public abstract class ClientType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    protected int maxRentedMachines;

    public ClientType() {
    }

    public String toString() {
        return "";
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
