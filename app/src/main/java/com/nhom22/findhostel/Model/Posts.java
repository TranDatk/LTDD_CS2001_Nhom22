package com.nhom22.findhostel.Model;

import java.util.Date;

public class Posts {
    private int id;
    private Date timeFrom;
    private Date timeTo;
    private String postName;
    private float price;
    private String description;
    private int activePost;
    private Address address;
    private UserAccount userAccount;
    private Type type;

    public Posts(int id, Date timeFrom, Date timeTo, String postName, float price,
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

    public Posts() {
        this.id = 1;
        this.timeFrom = null;
        this.timeTo = null;
        this.postName = "";
        this.price = 0;
        this.description = "";
        this.activePost = 1;
        this.address = null;
        this.userAccount = null;
        this.type = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
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
