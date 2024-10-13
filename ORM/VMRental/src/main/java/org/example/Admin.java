package org.example;

import jakarta.persistence.Entity;

@Entity
public class Admin extends ClientType{
    public Admin() {

    }
    public String toString() {
        return "Admin";
    }
}
