package com.nhom22.findhostel.Model;

public class Streets {
    private int id;
    private String name;
    private String isActive;
    private SubDistrics subDistrics;

    public Streets(int id, String name, String isActive, SubDistrics subDistrics) {

        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.subDistrics = subDistrics;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public SubDistrics getSubDistrics() {
        return subDistrics;
    }

    public void setSubDistrics(SubDistrics subDistrics) {
        this.subDistrics = subDistrics;
    }
}
