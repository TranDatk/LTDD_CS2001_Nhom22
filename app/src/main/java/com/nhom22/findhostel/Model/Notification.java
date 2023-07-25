package com.nhom22.findhostel.Model;

import java.util.Date;

public class Notification {
    private int id;
    private int posts;
    private int userAccount;
    private Date created_date;

    public Notification(int id, int posts, int userAccount, Date created_date) {
        this.id = id;
        this.posts = posts;
        this.userAccount = userAccount;
        this.created_date = created_date;
    }

    public Notification() {
        this.id = 0;
        this.posts = 0;
        this.userAccount = 0;
        this.created_date = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public int getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(int userAccount) {
        this.userAccount = userAccount;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
