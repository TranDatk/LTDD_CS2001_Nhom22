package com.nhom22.findhostel.Model;

public class SubDistricts {
    private int id;
    private String name;
    private int isActive;
    private Districts districts;

    public SubDistricts() {
        this.id = 0;
        this.name = "";
        this.isActive = 1;
        this.districts = null;
    }

    public SubDistricts(int id, String name, int isActive, Districts districts) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.districts = districts;
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

    public Districts getDistricts() {
        return districts;
    }

    public void setDistricts(Districts districts) {
        this.districts = districts;
    }
}
