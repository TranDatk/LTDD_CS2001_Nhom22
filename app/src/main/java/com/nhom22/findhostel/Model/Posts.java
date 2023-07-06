package com.nhom22.findhostel.Model;

import java.sql.Timestamp;

public class Posts {
    private int id;
    private Timestamp timeFrom;
    private Timestamp timeTo;
    private String postName;
    private float price;
    private String description;
    private int activePost;
    private Address address;
    private UserAccount userAccount;
    private Type type;

    public Posts(int id, Timestamp timeFrom, Timestamp timeTo, String postName, float price,
                 String description, int activePost, Address address, UserAccount userAccount, Type type) {
        this.id = id;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.postName = postName;
        this.price = price;
        this.description = description;
        this.activePost = activePost;
        this.address = address;
        this.userAccount = userAccount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Timestamp timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Timestamp getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Timestamp timeTo) {
        this.timeTo = timeTo;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActivePost() {
        return activePost;
    }

    public void setActivePost(int activePost) {
        this.activePost = activePost;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
