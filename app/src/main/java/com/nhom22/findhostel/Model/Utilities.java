package com.nhom22.findhostel.Model;

public class Utilities {
    private int id;
    private String name;
    private int isActive;

    public Utilities(int id, String name, int isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public Utilities() {
        this.id = 1;
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
