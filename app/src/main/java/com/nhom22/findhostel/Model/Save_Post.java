package com.nhom22.findhostel.Model;

public class Save_Post {
    private int id;
    private Posts posts;
    UserAccount userAccount;

    public Save_Post(int id, Posts posts, UserAccount userAccount) {
        this.id = id;
        this.posts = posts;
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

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
