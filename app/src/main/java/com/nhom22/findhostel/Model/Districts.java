package com.nhom22.findhostel.Model;

public class Districts {
    private int id;
    private String name;
    private int isActive;
    private Cities cities;

    public Districts(int id, String name, int isActive, Cities cities) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.cities = cities;
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

    public Cities getCities() {
        return cities;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }
}
