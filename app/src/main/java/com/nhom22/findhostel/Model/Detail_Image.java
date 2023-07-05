package com.nhom22.findhostel.Model;

public class Detail_Image {
    private int in;
    private Images images;
    private Posts posts;

    public Detail_Image(int in, Images images, Posts posts) {
        this.in = in;
        this.images = images;
        this.posts = posts;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
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
