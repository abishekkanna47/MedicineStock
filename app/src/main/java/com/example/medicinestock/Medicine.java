package com.example.medicinestock;

public class Medicine {
    private int id;
    private String name;
    private String expiryDate;
    private double price;

    // Constructors
    public Medicine() {
    }

    public Medicine(int id, String name, String expiryDate, double price) {
        this.id = id;
        this.name = name;
        this.expiryDate = expiryDate;
        this.price = price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Override toString() for ArrayAdapter
    @Override
    public String toString() {
        return name + " \n Expiry: " + expiryDate + " \n Price: $" + price;
    }
}
