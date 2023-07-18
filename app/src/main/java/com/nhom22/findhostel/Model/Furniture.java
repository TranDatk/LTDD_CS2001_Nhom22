package com.nhom22.findhostel.Model;

public class Furniture {
    private int id;
    private String name;
    private int isActive;

    public Furniture(int id, String name, int isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public Furniture() {
        this.id = 0;
        this.name = "";
        this.isActive = 1;
    }

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

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
