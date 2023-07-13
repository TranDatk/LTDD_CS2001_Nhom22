package com.nhom22.findhostel.Model;

public class Detail_Image {
    private int id;
    private Images images;
    private Posts posts;

    public Detail_Image(int id, Images images, Posts posts) {
        this.id = id;
        this.images = images;
        this.posts = posts;
    }

    public Detail_Image() {
        this.id = 1;
        this.images = null;
        this.posts = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }
}
