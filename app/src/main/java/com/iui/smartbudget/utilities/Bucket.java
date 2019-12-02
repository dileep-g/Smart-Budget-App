package com.iui.smartbudget.utilities;

public class Bucket {
    private String name;
    private float current;
    private float capacity;

    public Bucket(String name, float capacity) {
        this.current = 0.0f;
        this.capacity = capacity;
        this.name = name;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
