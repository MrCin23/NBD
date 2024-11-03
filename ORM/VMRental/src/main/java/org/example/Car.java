package org.example;


public class Car {

    private long carId;

    private String name;

    // Konstruktor, gettery, settery


    public Car(long carId, String name) {
        this.carId = carId;
        this.name = name;
    }

    public Car() {

    }

    // Getters and Setters
    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
