package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public abstract class ClientType {

    @Id
    private long id;

    protected int maxRentedMachines;

    public ClientType() {
    }

    public ClientType(long id) {
        this.id = id;
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
