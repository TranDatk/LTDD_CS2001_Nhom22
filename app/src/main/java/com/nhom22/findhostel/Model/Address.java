package com.nhom22.findhostel.Model;

public class Address {
    private int id;
    private String houseNumber;
    private int isActive;
    private Cities cities;
    private Districts districts;
    private SubDistricts subDistricts;
    private Streets streets;

    public Address(int id, String houseNumber, int isActive, Cities cities, Districts districts, SubDistricts subDistricts, Streets streets) {
        this.id = id;
        this.houseNumber = houseNumber;
        this.isActive = isActive;
        this.cities = cities;
        this.districts = districts;
        this.subDistricts = subDistricts;
        this.streets = streets;
    }

    public int getId() {
        return id;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public int getIsActive() {
        return isActive;
    }

    public Cities getCities() {
        return cities;
    }

    public Districts getDistricts() {
        return districts;
    }

    public SubDistricts getSubDistrics() {
        return subDistricts;
    }

    public Streets getStreets() {
        return streets;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }

    public void setDistricts(Districts districts) {
        this.districts = districts;
    }

    public void setSubDistrics(SubDistricts subDistricts) {
        this.subDistricts = subDistricts;
    }

    public void setStreets(Streets streets) {
        this.streets = streets;
    }
}
