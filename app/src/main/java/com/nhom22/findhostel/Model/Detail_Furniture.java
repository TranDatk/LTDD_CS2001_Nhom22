package com.nhom22.findhostel.Model;

public class Detail_Furniture {
    private int id;
    private int quantity;
    private Posts posts;
    private Furniture furniture;

    public Detail_Furniture(int id, int quantity, Posts posts, Furniture furniture) {
        this.id = id;
        this.quantity = quantity;
        this.posts = posts;
        this.furniture = furniture;
    }

    public Detail_Furniture() {
        this.id = 1;
        this.quantity = 0;
        this.posts = null;
        this.furniture = null;
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

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }
}
