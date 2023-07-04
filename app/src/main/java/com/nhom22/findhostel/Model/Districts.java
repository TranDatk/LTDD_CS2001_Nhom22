package com.nhom22.findhostel.Model;

public class Districts {
    private int id;
    private String name;
    private String isActive;
    private Cities cities;

    public Districts(int id, String name, String isActive, Cities cities) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.cities = cities;
    }
}
