package com.nhom22.findhostel.Model;

public class Images {
    private int id;
    private String name;
    private byte[] image;
    private int isActive;

    public Images(int id, String name, byte[] image, int isActive) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isActive = isActive;
    }

    public Images() {
        this.id = 1;
        this.name = "";
        this.image = null;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
