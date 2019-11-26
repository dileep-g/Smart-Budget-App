package com.iui.smartbudget.utilities;

public class Bucket {
    private String name;
    private double current;
    private double capacity;

    public Bucket(String name, double capacity) {
        this.current = 0.0;
        this.capacity = capacity;
        this.name = name;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
