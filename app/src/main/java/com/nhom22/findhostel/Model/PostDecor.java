package com.nhom22.findhostel.Model;

import java.util.Date;

public class PostDecor {
    private int id;
    private String text;
    private byte[] image;
    private Date created_date;
    private int user_id;
    private int isActive;

    public PostDecor(int id, String text, byte[] image, Date created_date, int user_id, int isActive) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.created_date = created_date;
        this.user_id = user_id;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public byte[] getImage() {
        return image;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
