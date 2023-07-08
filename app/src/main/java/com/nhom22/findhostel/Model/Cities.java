package com.nhom22.findhostel.Model;

public class Cities {
    private int id;
    private String name;
    private int isActive;

    public Cities() {
        this.id = 0;
        this.name = "";
        this.isActive = 1;
    }

    public Cities(int id, String name, int isActive) {
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

    public int getIsActive() {
        return isActive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
