package com.nhom22.findhostel.Model;

public class HostelCollection {
    private int id;
    private String title; // name of user, not have to login
    private byte[] image;
    private int address;

    public HostelCollection() {
    }

    public HostelCollection(int id, String title, byte[] image, int address) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
