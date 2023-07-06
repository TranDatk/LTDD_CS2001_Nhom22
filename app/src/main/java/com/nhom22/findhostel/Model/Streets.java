package com.nhom22.findhostel.Model;

public class Streets {
    private int id;
    private String name;
    private int isActive;
    private SubDistricts subDistricts;

    public Streets(int id, String name, int isActive, SubDistricts subDistricts) {

        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.subDistricts = subDistricts;
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

    public SubDistricts getSubDistrics() {
        return subDistricts;
    }

    public void setSubDistrics(SubDistricts subDistricts) {
        this.subDistricts = subDistricts;
    }
}
