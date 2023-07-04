package com.nhom22.findhostel.Model;

public class Cities {
    private int id;
    private String name;
    private String isActive;

    public Cities(int id, String name, String isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
