package com.nhom22.findhostel.model;

import java.util.Date;

public class PostDecor {
    private int id;
    private String text;
    private byte[] image;
    private String created_date;
    private int user_id;
    private int isActive;

    public PostDecor(int id, String text, byte[] image, String created_date, int user_id, int isActive) {
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

    public String getCreatedDate() {
        return created_date;
    }

    public int getUserId() {
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

    public void setCreatedDate(String created_date) {
        this.created_date = created_date;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
