package com.nhom22.findhostel.Model;

import java.util.Date;

public class Notification {
    private int id;
    private int postsId;
    private int userAccountId;
    private Date created_date;

    public Notification(int id, int postsId, int userAccountId, Date created_date) {
        this.id = id;
        this.postsId = postsId;
        this.userAccountId = userAccountId;
        this.created_date = created_date;
    }

    public Notification() {
        this.id = 0;
        this.postsId = 0;
        this.userAccountId = 0;
        this.created_date = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostsId() {
        return postsId;
    }

    public void setPostsId(int posts) {
        this.postsId = posts;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccount) {
        this.userAccountId = userAccount;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
