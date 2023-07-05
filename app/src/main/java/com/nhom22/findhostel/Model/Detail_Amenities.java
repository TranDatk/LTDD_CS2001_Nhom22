package com.nhom22.findhostel.Model;

public class Detail_Amenities {
    private int id;
    private int quantity;
    private Posts posts;
    private Amenities amenities;

    public Detail_Amenities(int id, int quantity, Posts posts, Amenities amenities) {
        this.id = id;
        this.quantity = quantity;
        this.posts = posts;
        this.amenities = amenities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public Amenities getAmenities() {
        return amenities;
    }

    public void setAmenities(Amenities amenities) {
        this.amenities = amenities;
    }
}
