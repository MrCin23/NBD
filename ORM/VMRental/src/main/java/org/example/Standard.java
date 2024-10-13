package org.example;

import jakarta.persistence.Entity;

@Entity
public class Standard extends ClientType{
    public Standard() {
    }
    public String toString() {
        return "Standard";
    }
}
