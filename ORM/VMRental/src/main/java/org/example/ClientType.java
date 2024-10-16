package org.example;

import jakarta.persistence.*;

@Entity
public abstract class ClientType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    protected int maxRentedMachines;

    protected String name;

    public ClientType() {
        this.name = getClass().getSimpleName();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
