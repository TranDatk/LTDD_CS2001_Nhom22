package com.nhom22.findhostel.Model;

public class Detail_Utilities {
    private int id;
    private double price;
    private String unit;
    private Posts posts;
    private Utilities utilities;

    public Detail_Utilities(int id, double price, String unit, Posts posts, Utilities utilities) {
        this.id = id;
        this.price = price;
        this.unit = unit;
        this.posts = posts;
        this.utilities = utilities;
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

    public Utilities getService() {
        return utilities;
    }

    public void setService(Utilities utilities) {
        this.utilities = utilities;
    }
}
