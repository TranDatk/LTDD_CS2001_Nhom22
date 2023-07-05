package com.nhom22.findhostel.Model;

public class Detail_Service {
    private int id;
    private double price;
    private String unit;
    private Posts posts;
    private Service service;

    public Detail_Service(int id, double price, String unit, Posts posts, Service service) {
        this.id = id;
        this.price = price;
        this.unit = unit;
        this.posts = posts;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
