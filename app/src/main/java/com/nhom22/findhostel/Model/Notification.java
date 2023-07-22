package com.nhom22.findhostel.Model;

import java.util.Date;

public class Notification {
    private int id;
    private Posts posts;
    private UserAccount userAccount;
    private String description;
    private Date created_date;


    public Notification(int id, Posts posts, UserAccount userAccount, String description, Date created_date) {
        this.id = id;
        this.posts = posts;
        this.userAccount = userAccount;
        this.description = description;
        this.created_date = created_date;
    }

    public Notification() {
        this.id = 0;
        this.posts = null;
        this.userAccount = null;
        this.description = "";
        this.created_date = null;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts_id) {
        this.posts = posts_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
